package com.marcel.Lanchonete.helper;

import com.marcel.Lanchonete.dto.OrderDTO;
import com.marcel.Lanchonete.model.Client;
import com.marcel.Lanchonete.model.Order;
import com.marcel.Lanchonete.util.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderHelper {
    
    @Autowired
    private Utils utils;

    public Order toOrder(OrderDTO orderDTO, Client client, Long lastId) {
        Order order = new Order();
        order.setClient(client);
        order.setIdentification(utils.generateRandomCode());
        order.setIdentification(utils.generateRandomCode()+String.valueOf(lastId));
        return order;
    }
}
