package com.endava.garagesale.utils;

import com.endava.garagesale.entity.Card;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class CardChecksTest {
    private final static String VALID_CARD_NUMBER = "1358954993914435";
    private final static String INVALID_CARD_NUMBER = "13589549939144";
    private final static YearMonth VALID_CARD_DATE = YearMonth.of(2022, 4);
    private final static YearMonth INVALID_CARD_DATE = YearMonth.of(2021, 3);

    private CardChecks cardChecks;

    @Before
    public void setUp() {
        cardChecks = new CardChecks();
    }

    @Test
    public void testCard() {
        Card card = new Card();
        card.setId(1L);
        card.setNumber("1111222233334444");
        card.setCvv("123");
        card.setExpDate(VALID_CARD_DATE);
        card.setName("Beni");

        assertEquals(1L, card.getId());
        assertEquals("Beni", card.getName());
        assertEquals("1111222233334444", card.getNumber());
        assertEquals("123", card.getCvv());
    }

    @Test
    public void cardNumberIsValid() {
        assertTrue(cardChecks.isCardNumberValid(VALID_CARD_NUMBER));
    }

    @Test
    public void cardNumberIsNotValid() {
        assertFalse(cardChecks.isCardNumberValid(INVALID_CARD_NUMBER));
    }

    @Test
    public void cardDateIsValid() {
        assertTrue(cardChecks.isCardDateValid(VALID_CARD_DATE));
    }

    @Test
    public void cardDateIsNotValid() {
        assertFalse(cardChecks.isCardDateValid(INVALID_CARD_DATE));
    }
}
