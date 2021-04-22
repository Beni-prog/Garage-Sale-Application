package com.endava.garagesale.controller;

import com.endava.garagesale.entity.Receipt;
import com.endava.garagesale.request.OrderRequest;
import com.endava.garagesale.service.OrderService;
import com.endava.garagesale.utils.CardUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents the order controller
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final CardUtils cardUtils;
    private final Logger log = LoggerFactory.getLogger(OrderController.class);


    @Autowired
    public OrderController(OrderService orderService, CardUtils cardUtils) {
        this.orderService = orderService;
        this.cardUtils = cardUtils;
    }

    /**
     * This function takes as input an order request and it creates an order and the correspondingly receipt
     *
     * @param orderRequest: OrderRequest
     * @return: a receipt for the new created order
     */
    @PostMapping("/pay")
    public ResponseEntity<Receipt> addOrder(@RequestBody OrderRequest orderRequest) {
        String cardNumber = orderRequest.getCard().getNumber();

        //hide card number and card cvv
        orderRequest.getCard().setNumber(cardUtils.maskCardNumber(cardNumber));
        orderRequest.getCard().setCvv(cardUtils.maskCVVNumber(orderRequest.getCard().getCvv()));

        log.info("\n-----------------------------------------------------------\n");
        log.info("\nRequest received for adding an order {}", orderRequest);
        return ResponseEntity.ok(orderService.createOrder(orderRequest, cardNumber));
    }
}
