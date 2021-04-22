package com.endava.garagesale.utils;

import com.endava.garagesale.entity.Order;
import com.endava.garagesale.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * this class deals with validating a purchased product
 */
@Component
public class OrderUtils {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderUtils(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * @param id: Long
     * @return: an order by a given id
     */
    public Order getOrderById(Long id) {
        for (Order order : orderRepository.findAll()) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }
}
