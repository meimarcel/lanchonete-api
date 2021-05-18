package com.marcel.Lanchonete.assembler;

import com.marcel.Lanchonete.controller.OrderController;
import com.marcel.Lanchonete.model.Order;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class OrderManagerAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>>{

    @Override
    public EntityModel<Order> toModel(Order entity) {
        return EntityModel.of(entity,
            linkTo(methodOn(OrderController.class).getOrder(entity.getId(), null)).withSelfRel(),
            linkTo(methodOn(OrderController.class).listOrders(entity.getClient().getEmail(), "", null)).withRel("list"),
            linkTo(methodOn(OrderController.class).confirmOrder(entity.getId())).withRel("confirm"),
            linkTo(methodOn(OrderController.class).cancelOrder(entity.getId())).withRel("cancel"),
            linkTo(methodOn(OrderController.class).finishOrder(entity.getId())).withRel("finish"),
            linkTo(methodOn(OrderController.class).deleteOrder(entity.getId())).withRel("delete"));
    }
    
}
