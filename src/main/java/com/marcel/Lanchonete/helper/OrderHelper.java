package com.marcel.Lanchonete.helper;

import com.marcel.Lanchonete.dto.OrderDTO;
import com.marcel.Lanchonete.model.Client;
import com.marcel.Lanchonete.model.Order;

import org.springframework.stereotype.Component;

@Component
public class OrderHelper {

    public Order toOrder(OrderDTO orderDTO, Client client, String identification) {
        Order order = new Order();
        order.setClient(client);
        order.setIdentification(identification);
        return order;
    }
}
