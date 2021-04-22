package com.endava.garagesale.utils;

import org.springframework.stereotype.Component;

import java.time.YearMonth;

/**
 * This class check whether a card is valid, i.e. the card number is valid and the date is greater
 * than the current date
 */
@Component
public class CardChecks {
    private static boolean IS_SECOND = false;

    private static int NUMBER_SUM = 0;

    /**
     * @param cardNumber: String
     * @return: true, if card is valid, false otherwise
     */
    public boolean isCardNumberValid(String cardNumber) {
        int NUMBER_OF_DIGITS = cardNumber.length();
        if (NUMBER_OF_DIGITS < 16) {
            return false;
        } else {
            for (int i = NUMBER_OF_DIGITS - 1; i >= 0; i--) {

                int DIGIT = cardNumber.charAt(i) - '0';

                if (IS_SECOND)
                    DIGIT = DIGIT * 2;

                // We add two digits to handle cases that make two digits after doubling
                NUMBER_SUM += DIGIT / 10;
                NUMBER_SUM += DIGIT % 10;

                IS_SECOND = !IS_SECOND;
            }
            return (NUMBER_SUM % 10 == 0);
        }
    }

    /**
     * @param cardDate: YearMonth
     * @return true, if the card date is valid, false otherwise
     */
    public boolean isCardDateValid(YearMonth cardDate) {
        if (cardDate.compareTo(YearMonth.now()) >= 0) {
            return true;
        }
        return false;
    }
}
