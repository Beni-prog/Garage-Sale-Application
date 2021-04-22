package com.endava.garagesale.entity;

import javax.persistence.Entity;
import java.time.YearMonth;

/**
 * This class creates a receipt that treats the invalid card date status
 */
@Entity(name = "ReceiptInvalidCardDate")
public class ReceiptInvalidCardDate extends Receipt {
    private final YearMonth expDate;

    public ReceiptInvalidCardDate(YearMonth expDate) {
        this.expDate = expDate;
        this.status = Order.Status.CARD_DATE_EXPIRED;
    }

    public YearMonth getExpDate() {
        return expDate;
    }

    @Override
    public String toString() {
        return "ReceiptInvalidCardDate{" +
                "status=" + status +
                ", expDate=" + expDate +
                '}';
    }
}
