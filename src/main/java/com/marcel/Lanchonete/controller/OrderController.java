package com.marcel.Lanchonete.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import com.marcel.Lanchonete.assembler.OrderAssembler;
import com.marcel.Lanchonete.assembler.OrderManagerAssembler;
import com.marcel.Lanchonete.dto.ItemDTO;
import com.marcel.Lanchonete.dto.OrderDTO;
import com.marcel.Lanchonete.enums.OrderStatus;
import com.marcel.Lanchonete.helper.ItemHelper;
import com.marcel.Lanchonete.helper.OrderHelper;
import com.marcel.Lanchonete.model.Client;
import com.marcel.Lanchonete.model.Item;
import com.marcel.Lanchonete.model.Order;
import com.marcel.Lanchonete.repository.OrderRepository;
import com.marcel.Lanchonete.util.Utils;
import com.marcel.Lanchonete.repository.ClientRepository;
import com.marcel.Lanchonete.error.ResourceNotFoundException;
import com.marcel.Lanchonete.error.UserNotFoundException;
import com.marcel.Lanchonete.error.InfoException;
import com.marcel.Lanchonete.error.MasterDetails;
import com.marcel.Lanchonete.enums.DetailType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class OrderController {
    
    @Autowired
    private OrderRepository orderDAO;

    @Autowired
    private ClientRepository clientDAO;

    @Autowired
    private OrderHelper orderHelper;

    @Autowired
    private ItemHelper itemHelper;

    @Autowired
    private OrderAssembler orderAssembler;

    @Autowired
    private OrderManagerAssembler orderManagerAssembler;

    @Autowired
    private PagedResourcesAssembler<Order> pagedResourcesAssembler;

    @Autowired
    private Utils utils;

    @PostMapping("/public/order/create")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Client client = clientDAO.findById(orderDTO.getClient())
            .orElseThrow(() -> new UserNotFoundException("Cliente não encontrado."));
        
        Long lastId = orderDAO.count();
        String identification = utils.generateRandomCode()+String.valueOf(lastId);
        
        Order test = orderDAO.findByIdentification(identification).orElse(null);
        while(test != null) {
            identification = utils.generateRandomCode()+String.valueOf(lastId);
            test = orderDAO.findByIdentification(identification).orElse(null);
        }
        
        Order order = orderHelper.toOrder(orderDTO, client, identification);

        List<Item> itemList = new ArrayList<>();
        if(orderDTO.getItems() != null) {
            if(!orderDTO.getItems().isEmpty()) {
                for(ItemDTO i : orderDTO.getItems()) {
                    itemList.add(itemHelper.toItem(i, order));
                }
            }
        }
        order.setItems(itemList);

        EntityModel<Order> orderEntity = orderAssembler.toModel(orderDAO.save(order));

        return ResponseEntity
            .created(orderEntity.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(orderEntity);
    }

    @GetMapping("/admin/order/all")
    public ResponseEntity<?> listOrders(@PathParam("client") String client, @PathParam("status") String status, Pageable pageable) {
        Page<Order> orderPage = null;
        if((client == null || client.isEmpty()) && (status == null || status.isEmpty())) {
            orderPage = orderDAO.findAll(pageable);
        } else if((client != null && !client.isEmpty()) && (status == null || status.isEmpty())) {
            Client clientModel = clientDAO.findById(client).orElse(null);
            orderPage = orderDAO.findByClient(clientModel, pageable);
        } else if((client == null || client.isEmpty()) && (status != null && !status.isEmpty())) {
            orderPage = orderDAO.findByStatus(OrderStatus.parser(status), pageable);
        } else {
            Client clientModel = clientDAO.findById(client).orElse(null);
            orderPage = orderDAO.findByClientAndStatus(clientModel, OrderStatus.parser(status), pageable);
        }

        PagedModel<EntityModel<Order>> orderPagedModel = pagedResourcesAssembler.toModel(orderPage, orderManagerAssembler);
        
        return ResponseEntity.ok(orderPagedModel);
    }

    @GetMapping("/public/order/byClient/{client}")
    public ResponseEntity<?> listOrdersByClient(@PathVariable("client") String client, @PathParam("status") String status, Pageable pageable) {
        Client clientModel = clientDAO.findById(client)
            .orElseThrow(() -> new UserNotFoundException("Cliente não encontrado."));
        Page<Order> orderPage = null;
        if(status != null && !status.isEmpty()) {
            orderPage = orderDAO.findByClientAndStatus(clientModel, OrderStatus.parser(status), pageable);
        } else {
            orderPage = orderDAO.findByClient(clientModel, pageable);
        }

        PagedModel<EntityModel<Order>> orderPagedModel = pagedResourcesAssembler.toModel(orderPage, orderAssembler);
        
        return ResponseEntity.ok(orderPagedModel);
    }

    @GetMapping("/public/order/byId/{id}")
    public ResponseEntity<?> getOrder(@PathVariable("id") Long id, Authentication authentication) {
        Order order = orderDAO.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        if(authentication == null) {
            return ResponseEntity.ok(orderAssembler.toModel(order));
        } else {
            return ResponseEntity.ok(orderManagerAssembler.toModel(order));
        }
    }

    @GetMapping("/public/order/byIdentification/{identification}")
    public ResponseEntity<?> getOrderByIdentification(@PathVariable("identification") String identification, Authentication authentication) {
        Order order = orderDAO.findByIdentification(identification)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        if(authentication == null) {
            return ResponseEntity.ok(orderAssembler.toModel(order));
        } else {
            return ResponseEntity.ok(orderManagerAssembler.toModel(order));
        }
    }

    @PutMapping("/admin/order/confirm/{id}")
    public ResponseEntity<?> confirmOrder(@PathVariable("id") Long id) {
        Order order = orderDAO.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        if(order.getStatus() == OrderStatus.FINALIZADO) {
            throw new InfoException("Não foi possível completar a operação, o pedido se encontra finalizado.", DetailType.ERROR);
        }
        if(order.getStatus() == OrderStatus.CANCELADO) {
            throw new InfoException("Não foi possível completar a operação, o pedido se encontra cancelado.", DetailType.ERROR);
        }
        if(order.getStatus() == OrderStatus.EM_ANDAMENTO) {
            throw new InfoException("O pedido já se encontra em andamento.", DetailType.WARNING);
        }

        order.setStatus(OrderStatus.EM_ANDAMENTO);
        EntityModel<Order> orderEntity = orderAssembler.toModel(orderDAO.save(order));
    
        return ResponseEntity
            .created(orderEntity.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(orderEntity);
    }

    @PutMapping("/admin/order/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable("id") Long id) {
        Order order = orderDAO.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        if(order.getStatus() == OrderStatus.FINALIZADO) {
            throw new InfoException("Não foi possível completar a operação, o pedido se encontra finalizado.", DetailType.ERROR);
        }
        if(order.getStatus() == OrderStatus.CANCELADO) {
            throw new InfoException("O pedido já se encontra cancelado.", DetailType.WARNING);
        }

        order.setStatus(OrderStatus.CANCELADO);
        EntityModel<Order> orderEntity = orderAssembler.toModel(orderDAO.save(order));
    
        return ResponseEntity
            .created(orderEntity.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(orderEntity);
    }

    @PutMapping("/admin/order/finish/{id}")
    public ResponseEntity<?> finishOrder(@PathVariable("id") Long id) {
        Order order = orderDAO.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        if(order.getStatus() == OrderStatus.CANCELADO) {
            throw new InfoException("Não foi possível completar a operação, o pedido se encontra cancelado.", DetailType.ERROR);
        }
        if(order.getStatus() == OrderStatus.FINALIZADO) {
            throw new InfoException("O pedido já se encontra finalizado.", DetailType.WARNING);
        }

        order.setStatus(OrderStatus.FINALIZADO);
        EntityModel<Order> orderEntity = orderAssembler.toModel(orderDAO.save(order));
    
        return ResponseEntity
            .created(orderEntity.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(orderEntity);
    }

    @PutMapping("/public/order/cancel/{id}")
    public ResponseEntity<?> cancelOrderByClient(@PathVariable("id") Long id) {
        Order order = orderDAO.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        if(order.getStatus() == OrderStatus.FINALIZADO) {
            throw new InfoException("Não foi possível completar a operação, o pedido se encontra finalizado.", DetailType.ERROR);
        }
        if(order.getStatus() == OrderStatus.EM_ANDAMENTO) {
            throw new InfoException("Não foi possível completar a operação, o pedido se encontra em andamento.", DetailType.ERROR);
        }
        if(order.getStatus() == OrderStatus.CANCELADO) {
            throw new InfoException("O pedido já se encontra cancelado.", DetailType.WARNING);
        }
        
        order.setStatus(OrderStatus.CANCELADO);
        EntityModel<Order> orderEntity = orderAssembler.toModel(orderDAO.save(order));
    
        return ResponseEntity
            .created(orderEntity.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(orderEntity);
    }

    @DeleteMapping("/admin/order/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        Order order = orderDAO.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));
        orderDAO.delete(order);

        return ResponseEntity.ok(MasterDetails.Builder
            .newBuilder()
            .title("Information")
            .message("Pedido deletado com sucesso.")
            .type(DetailType.SUCCESS)
            .timestamp(LocalDateTime.now())
            .build());
    }

}
