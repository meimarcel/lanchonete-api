package com.marcel.Lanchonete.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import com.marcel.Lanchonete.assembler.ProductAssembler;
import com.marcel.Lanchonete.assembler.ProductManagerAssembler;
import com.marcel.Lanchonete.dto.ProductDTO;
import com.marcel.Lanchonete.enums.ProductType;
import com.marcel.Lanchonete.error.ResourceNotFoundException;
import com.marcel.Lanchonete.helper.ProductHelper;
import com.marcel.Lanchonete.error.MasterDetails;
import com.marcel.Lanchonete.enums.DetailType;
import com.marcel.Lanchonete.model.Item;
import com.marcel.Lanchonete.model.Product;
import com.marcel.Lanchonete.repository.ItemRepository;
import com.marcel.Lanchonete.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
public class ProductController {
    
    @Autowired
    private ProductRepository productDAO;

    @Autowired
    private ItemRepository itemDAO;

    @Autowired
    private ProductAssembler productAssembler;

    @Autowired
    private ProductManagerAssembler productManagerAssembler;

    @Autowired
    private ProductHelper productHelper;

    @Autowired
    private PagedResourcesAssembler<Product> pagedResourcesAssembler;

    @PostMapping("/admin/product/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
        EntityModel<Product> productEntity = productManagerAssembler.toModel(productDAO.save(product));
        
        return ResponseEntity
            .created(productEntity.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(productEntity);
    }

    @GetMapping("/public/product/all")
    public ResponseEntity<?> listProducts(@PathParam("name") String name, @PathParam("type") String type, @PageableDefault(size = 10, sort = {"name"}, direction = Direction.ASC) Pageable pageable, Authentication authentication) {
        Page<Product> productPage = null;
        if((name == null || name.isEmpty()) && (type == null || type.isEmpty())) {
            productPage = productDAO.findAll(pageable);
        } else if((name != null && !name.isEmpty()) && (type == null || type.isEmpty())) {
            productPage = productDAO.findByNameIgnoreCaseContaining(name, pageable);
        } else if((name == null || name.isEmpty()) && (type != null && !type.isEmpty())) {
            productPage = productDAO.findByType(ProductType.parser(type), pageable);
        } else {
            productPage = productDAO.findByNameIgnoreCaseContainingAndType(name, ProductType.parser(type), pageable);
        }

        PagedModel<EntityModel<Product>> productPagedModel = null;
        if(authentication == null) {
            productPagedModel = pagedResourcesAssembler.toModel(productPage, productAssembler);
        } else {
            productPagedModel = pagedResourcesAssembler.toModel(productPage, productManagerAssembler);
        }
        return ResponseEntity.ok(productPagedModel);
    }

    @GetMapping("/public/product/byId/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id, Authentication authentication) {
        Product product = productDAO.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

        if(authentication == null) {
            return ResponseEntity.ok(productAssembler.toModel(product));
        } else {
            return ResponseEntity.ok(productManagerAssembler.toModel(product));
        }
    }

    @PutMapping("/admin/product/update")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productDAO.findById(productDTO.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));
        product = productHelper.toProduct(product, productDTO);
        EntityModel<Product> productEntity = productManagerAssembler.toModel(productDAO.save(product));

        return ResponseEntity
            .created(productEntity.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(productEntity);
    }

    @DeleteMapping("/admin/product/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        Product product = productDAO.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));
        
        List<Item> itemList = product.getItems();
        for(Item item : itemList) {
            item.setProduct(null);
            itemDAO.save(item);
        }
        productDAO.delete(product);

        return ResponseEntity.ok(MasterDetails.Builder
            .newBuilder()
            .title("Information")
            .message("Produto deletado com sucesso.")
            .type(DetailType.SUCCESS)
            .timestamp(LocalDateTime.now())
            .build());
    }

}
