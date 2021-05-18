package com.marcel.Lanchonete.repository;

import java.util.Optional;

import com.marcel.Lanchonete.enums.OrderStatus;
import com.marcel.Lanchonete.model.Client;
import com.marcel.Lanchonete.model.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    Page<Order> findByClient(Client client, Pageable pageable);
    Page<Order> findByClientAndStatus(Client client, OrderStatus status, Pageable pageable);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    Optional<Order> findByIdentification(String identification);
}
