package com.marcel.Lanchonete.assembler;

import com.marcel.Lanchonete.controller.OrderController;
import com.marcel.Lanchonete.model.Order;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class OrderAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>>{

    @Override
    public EntityModel<Order> toModel(Order entity) {
        return EntityModel.of(entity,
            linkTo(methodOn(OrderController.class).getOrder(entity.getId(), null)).withSelfRel(),
            linkTo(methodOn(OrderController.class).createOrder(null)).withRel("create"),
            linkTo(methodOn(OrderController.class).listOrdersByClient(entity.getClient().getEmail(), "", null)).withRel("list"),
            linkTo(methodOn(OrderController.class).cancelOrderByClient(entity.getId())).withRel("cancel"));
    }
    
}
