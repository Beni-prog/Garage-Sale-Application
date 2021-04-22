package com.endava.garagesale.entity;

import java.time.YearMonth;
import java.util.List;

/**
 * This class returns a different receipt depending on the order status
 */
public class ReceiptFactory {
    /**
     * @param user: User
     * @param cardNumber: String
     * @param expDate: YearMonth
     * @param purchasedProducts: List<String>
     * @param sum: double
     * @param orderStatus: Order.Status
     * @return: a different type of receipt, depending on the order status
     */
    public static Receipt getReceipt(User user, String cardNumber, YearMonth expDate, List<String> purchasedProducts, double sum, Order.Status orderStatus) {
        switch (orderStatus) {
            case PAYED: {
                return new ReceiptSuccessfulOrder(user, cardNumber, purchasedProducts, sum, orderStatus);
            }
            case INVALID_CARD_NUMBER: {
                return new ReceiptInvalidCardNumber(cardNumber);
            }
            case CARD_DATE_EXPIRED: {
                return new ReceiptInvalidCardDate(expDate);
            }
            case USER_ACCOUNT_BLOCKED: {
                return new ReceiptInvalidUserAccount(user);
            }
            case PRODUCT_FROM_THE_SAME_CATEGORY: {
                return new ReceiptSameCategory(purchasedProducts);
            }
            case PRODUCT_OUT_OF_STOCK: {
                return new ReceiptProductOutOfStock(purchasedProducts);
            }
            case NONEXISTENT_USER:{
                return new ReceiptNonExistentUser();
            }
            default: {
                return null;
            }
        }
    }
}
