package com.endava.garagesale.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CardUtilsTest {
    private CardUtils cardUtils;

    private static String CVV = "123";
    private static String CVV_MASKED = "*2*";
    private static String CARD_NUMBER = "1111222233334444";
    private static String CARD_NUMBER_MASK = "####********####";
    private static String CARD_NUMBER_MASKED = "1111********4444";


    @Before
    public void setUp() {
        cardUtils = new CardUtils();
    }

    @Test
    public void maskCardNumberTest() {
        assertEquals(CARD_NUMBER_MASKED, cardUtils.maskCardNumber(CARD_NUMBER));
    }

    @Test
    public void maskCVVNumberTest() {
        assertEquals(CVV_MASKED, cardUtils.maskCVVNumber(CVV));
    }

}
