package com.endava.garagesale.service;

import com.endava.garagesale.entity.*;
import com.endava.garagesale.repository.OrderRepository;
import com.endava.garagesale.repository.ReceiptRepository;
import com.endava.garagesale.repository.UserRepository;
import com.endava.garagesale.request.OrderRequest;
import com.endava.garagesale.utils.ProductUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BuildAndPersist {
    private final UserRepository userRepository;
    private final ReceiptRepository receiptRepository;
    private final OrderRepository orderRepository;
    private final ProductUtils productUtils;

    private final Logger log = LoggerFactory.getLogger(BuildAndPersist.class);

    @Autowired
    public BuildAndPersist(UserRepository userRepository, ReceiptRepository receiptRepository, OrderRepository orderRepository, ProductUtils productUtils) {
        this.userRepository = userRepository;
        this.receiptRepository = receiptRepository;
        this.orderRepository = orderRepository;
        this.productUtils = productUtils;
    }

    Receipt getReceipt(OrderRequest orderRequest, String cardNumber, Card card, User user, Order.Status status) {
        Order order = this.buildAndPersist(orderRequest, status);
        Receipt receipt = ReceiptFactory.getReceipt(user, cardNumber, card.getExpDate(),
                productUtils.getPurchasedProductsNames(order.getId()), order.getTotalAmount(), order.getStatus());
        receiptRepository.save(receipt);
        return receipt;
    }

    /**
     * This method creates and saves an order
     *
     * @param orderRequest: OrderRequest
     * @param orderStatus:  Order.Status
     * @return: void
     */
    public Order buildAndPersist(OrderRequest orderRequest, Order.Status orderStatus) {
        User user = userRepository.findById(orderRequest.getUserId()).get();
        Date currentDate = new Date();
        List<Product> purchasedProducts = productUtils.getPurchasedProducts(orderRequest.getProductsIds());

        Order order = Order.OrderBuilder.anOrder()
                .withProductList(purchasedProducts)
                .withCreatedDate(currentDate)
                .withStatus(orderStatus)
                .withUser(user)
                .withTotalAmount(productUtils.getTotalSum(orderRequest.getProductsIds()))
                .build();

        log.info("Creating the order with status {}", order.getStatus());
        return orderRepository.save(order);
    }
}
