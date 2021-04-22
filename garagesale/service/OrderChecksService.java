package com.endava.garagesale.service;

import com.endava.garagesale.entity.User;
import com.endava.garagesale.exceptions.*;
import com.endava.garagesale.request.OrderRequest;
import com.endava.garagesale.utils.CardChecks;
import com.endava.garagesale.utils.ProductChecks;
import com.endava.garagesale.utils.ProductUtils;
import com.endava.garagesale.utils.UserChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderChecksService {
    private CardChecks cardChecks;
    private UserChecks userChecks;
    private ProductChecks productChecks;
    private ProductUtils productUtils;

    @Autowired
    public OrderChecksService(CardChecks cardChecks, UserChecks userChecks, ProductChecks productChecks, ProductUtils productUtils) {
        this.cardChecks = cardChecks;
        this.userChecks = userChecks;
        this.productChecks = productChecks;
        this.productUtils = productUtils;
    }

    public void executeOrderChecks(OrderRequest orderRequest, String cardNumber, User user) throws InvalidCardNumberException, InvalidCardDateException, InactiveAccountException, ProductFromTheSameCategoryException, ProductOutOfStockException {

        if (!cardChecks.isCardNumberValid(cardNumber)) {
            throw new InvalidCardNumberException();
        }
        if (!cardChecks.isCardDateValid(orderRequest.getCard().getExpDate())) {
            throw new InvalidCardDateException();
        }
        if (!userChecks.isAccountActive(user.getAccountStatus())) {
            throw new InactiveAccountException();
        }
        if (productUtils.isFromTheSameCategory(orderRequest.getProductsIds())) {
            throw new ProductFromTheSameCategoryException();
        }
        for (Long id : orderRequest.getProductsIds()) {
            if (!productChecks.productExistsOnStock(id)) {
                throw new ProductOutOfStockException();
            }
        }
    }
}
