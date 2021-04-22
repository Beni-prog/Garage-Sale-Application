package com.endava.garagesale.entity;

import com.endava.garagesale.request.OrderRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class OrderRequestTest {

    @Test
    public void testOrderRequest() {
        OrderRequest orderRequest = new OrderRequest();

        orderRequest.setCard(new Card());
        orderRequest.setProductsIds(new ArrayList<>());
        orderRequest.setUserId(1L);

        assertEquals(1L, orderRequest.getUserId());
        assertEquals(new Card(), orderRequest.getCard());
        assertEquals(new ArrayList<>(), orderRequest.getProductsIds());
    }
}
