package com.endava.garagesale.utils;

import org.springframework.stereotype.Component;

/**
 * this class deals with masking a card number
 */
@Component
public class CardUtils {
    final static int STARTLENGTH = 4;   //first digit of card you don't want to mask
    final static int ENDLENGTH = 4;    //last digit of card you don't want to mask

    /**
     * @param cardNumber: Long
     * @return: the card number, but only the first 4 digits are visible,
     * the rest of it is marked as *
     */
    public String maskCardNumber(String cardNumber) {

        int maskedLength = cardNumber.length() - (STARTLENGTH + ENDLENGTH);
        System.out.println(maskedLength);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maskedLength; i++) {
            sb.append("*");
        }
        return cardNumber.substring(0, STARTLENGTH) + sb + cardNumber.substring(cardNumber.length() - ENDLENGTH, cardNumber.length());
    }

    /**
     * @param cardCVV: String
     * @return: the card cvv hidden
     */
    public String maskCVVNumber(String cardCVV) {

        return "*" + cardCVV.charAt(1) + "*";
    }
}
