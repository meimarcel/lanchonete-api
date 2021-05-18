package com.marcel.Lanchonete.assembler;

import com.marcel.Lanchonete.controller.ClientController;
import com.marcel.Lanchonete.model.Client;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ClientAssembler implements RepresentationModelAssembler<Client, EntityModel<Client>>{

    @Override
    public EntityModel<Client> toModel(Client entity) {
        return EntityModel.of(entity,
            linkTo(methodOn(ClientController.class).getClient(entity.getEmail())).withSelfRel(),
            linkTo(methodOn(ClientController.class).updateClient(null)).withRel("update"),
            linkTo(methodOn(ClientController.class).deleteClient(entity.getEmail())).withRel("delete"));
    }
    
}
