package com.endava.garagesale.service;

import com.endava.garagesale.entity.Receipt;
import com.endava.garagesale.request.OrderRequest;

/**
 * Represents the  service interface for Order
 */
public interface OrderService {
    Receipt createOrder(OrderRequest orderRequest, String cardNumber);
}
