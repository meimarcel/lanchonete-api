package com.marcel.Lanchonete.helper;

import com.marcel.Lanchonete.dto.ItemDTO;
import com.marcel.Lanchonete.error.ResourceNotFoundException;
import com.marcel.Lanchonete.model.Item;
import com.marcel.Lanchonete.model.Order;
import com.marcel.Lanchonete.model.Product;
import com.marcel.Lanchonete.repository.ProductRepository;
import com.marcel.Lanchonete.error.InfoException;
import com.marcel.Lanchonete.enums.DetailType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemHelper {
    
    @Autowired
    private ProductRepository productDAO;

    public Item toItem(ItemDTO itemDTO, Order order) {
        Item item = new Item();
        Product product = productDAO.findById(itemDTO.getProduct())
            .orElseThrow(() -> new ResourceNotFoundException("Produto com não encontrado (Id: "+itemDTO.getProduct()+")."));
        if(!product.getAvailable()) {
            throw new InfoException("Produto indisponível (Id: "+itemDTO.getProduct()+")", DetailType.ERROR);
        }
        
        item.setProduct(product);
        item.setQuantity(itemDTO.getQuantity());
        item.setOrder(order);
        return item;
    }
}
