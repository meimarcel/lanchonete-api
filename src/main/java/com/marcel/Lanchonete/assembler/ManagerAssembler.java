package com.marcel.Lanchonete.assembler;

import com.marcel.Lanchonete.controller.ManagerController;
import com.marcel.Lanchonete.model.Manager;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ManagerAssembler implements RepresentationModelAssembler<Manager, EntityModel<Manager>>{

    @Override
    public EntityModel<Manager> toModel(Manager entity) {
        return EntityModel.of(entity,
            linkTo(methodOn(ManagerController.class).getManager(null)).withSelfRel(),
            linkTo(methodOn(ManagerController.class).updateManager(null, null)).withRel("update"),
            linkTo(methodOn(ManagerController.class).updatePassword(null, null)).withRel("update-password"),
            linkTo(methodOn(ManagerController.class).deleteManagerByManager(null, null)).withRel("delete"));
    }
    
}
