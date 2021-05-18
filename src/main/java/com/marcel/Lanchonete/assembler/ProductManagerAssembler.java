package com.marcel.Lanchonete.assembler;

import com.marcel.Lanchonete.controller.ProductController;
import com.marcel.Lanchonete.model.Product;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductManagerAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>>{

    @Override
    public EntityModel<Product> toModel(Product entity) {
        return EntityModel.of(entity,
            linkTo(methodOn(ProductController.class).getProduct(entity.getId(), null)).withSelfRel(),
            linkTo(methodOn(ProductController.class).createProduct(null)).withRel("create"),
            linkTo(methodOn(ProductController.class).listProducts("", "", null, null)).withRel("list"),
            linkTo(methodOn(ProductController.class).updateProduct(null)).withRel("update"),
            linkTo(methodOn(ProductController.class).deleteProduct(entity.getId())).withRel("delete"));
    }
    
}
