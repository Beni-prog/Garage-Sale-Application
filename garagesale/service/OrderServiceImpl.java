package com.endava.garagesale.service;

import com.endava.garagesale.entity.*;
import com.endava.garagesale.exceptions.*;
import com.endava.garagesale.repository.ReceiptRepository;
import com.endava.garagesale.repository.UserRepository;
import com.endava.garagesale.request.OrderRequest;
import com.endava.garagesale.utils.ProductUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Operations on order service
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final ReceiptRepository receiptRepository;
    private final ProductUtils productUtils;
    private final UserRepository userRepository;
    private final OrderChecksService orderChecksService;
    private final UserChecksService userChecksService;

    private final BuildAndPersist buildAndPersist;

    @Autowired
    public OrderServiceImpl(ReceiptRepository receiptRepository, ProductUtils productUtils, UserRepository userRepository, OrderChecksService orderChecksService, UserChecksService userChecksService, BuildAndPersist buildAndPersist) {
        this.receiptRepository = receiptRepository;
        this.productUtils = productUtils;
        this.userRepository = userRepository;
        this.orderChecksService = orderChecksService;
        this.userChecksService = userChecksService;
        this.buildAndPersist = buildAndPersist;
    }

    /**
     * This method receives an order request and the number of the card unmasked
     * and it creates the order made by the user. Further, it checks whether the card is valid, i.e. the date is not expired
     * and the number is a valid one,then it check whether the user exists in database and if the account is active
     * Lastly, it check whether the desired products are from different categories, all of them. During this process, the order
     * status is updated correspondingly
     *
     * @param orderRequest: OrderRequest
     * @param cardNumber:   String
     * @return: a receipt regarding the details of the order
     */
    @Override
    public Receipt createOrder(OrderRequest orderRequest, String cardNumber) {
        Card card = orderRequest.getCard();
        User user;
        if(userRepository.existsById(orderRequest.getUserId())) {
            user = userRepository.findById(orderRequest.getUserId()).get();

            try {
                orderChecksService.executeOrderChecks(orderRequest, cardNumber, user);
            } catch (InvalidCardNumberException e) {
                return buildAndPersist.getReceipt(orderRequest, cardNumber, card, user, Order.Status.INVALID_CARD_NUMBER);
            } catch (InvalidCardDateException e) {
                return buildAndPersist.getReceipt(orderRequest, cardNumber, card, user, Order.Status.CARD_DATE_EXPIRED);
            } catch (InactiveAccountException e) {
                return buildAndPersist.getReceipt(orderRequest, cardNumber, card, user, Order.Status.USER_ACCOUNT_BLOCKED);
            } catch (ProductFromTheSameCategoryException e) {
                return buildAndPersist.getReceipt(orderRequest, cardNumber, card, user, Order.Status.PRODUCT_FROM_THE_SAME_CATEGORY);
            } catch (ProductOutOfStockException e) {
                return buildAndPersist.getReceipt(orderRequest, cardNumber, card, user, Order.Status.PRODUCT_OUT_OF_STOCK);
            }

            Order order = buildAndPersist.buildAndPersist(orderRequest, Order.Status.PAYED);

            //update stock
            productUtils.updateStock(orderRequest.getProductsIds());

            for (Product product : productUtils.getPurchasedProducts(orderRequest.getProductsIds())) {
                order.addProduct(product);
            }

            Receipt receipt = ReceiptFactory.getReceipt(user, card.getNumber(), card.getExpDate(),
                    productUtils.getPurchasedProductsNames(order.getId()), order.getTotalAmount(), order.getStatus());
            receiptRepository.save(receipt);
            log.info("Creating the receipt for the new created order {}", receipt);

            return receipt;
        }else
            return new ReceiptNonExistentUser();
    }
}
