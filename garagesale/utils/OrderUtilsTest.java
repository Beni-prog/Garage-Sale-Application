package com.endava.garagesale.utils;

import com.endava.garagesale.entity.Order;
import com.endava.garagesale.entity.Product;
import com.endava.garagesale.entity.User;
import com.endava.garagesale.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.endava.garagesale.entity.Order.Status.PAYED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class OrderUtilsTest {
    private OrderUtils orderUtils;
    private OrderRepository orderRepositoryOrderUtils;

    private static Long ORDER_ID = 1L;

    @Before
    public void setUp() {
        orderRepositoryOrderUtils = Mockito.mock(OrderRepository.class);
        orderUtils = new OrderUtils(orderRepositoryOrderUtils);
    }

    @Test
    public void testOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(PAYED);
        order.setTotalAmount(100);
        order.setProductList(new ArrayList<>());
        order.setUser(new User());

        assertEquals(1L, order.getId());
        assertEquals(PAYED, order.getStatus());
        assertEquals(100, order.getTotalAmount());
        assertEquals(new ArrayList<>(), order.getProductList());
        assertEquals(new User(), order.getUser());
    }

    @Test
    public void getOrderByIdTest() {
        Product product = new Product(1L, "100", "Product1", 100, new ArrayList(), 10, Product.Category.LAPTOPS);
        List<Product> products = new ArrayList<>();
        products.add(product);
        Order order = new Order(products, new Date(), PAYED, new User(), 0.00);
        order.setId(ORDER_ID);
        List<Order> orders = new ArrayList<Order>();
        orders.add(order);
        Iterable<Order> orderIterable = orders;

        doReturn(orderIterable).when(orderRepositoryOrderUtils).findAll();

        assertEquals(order, orderUtils.getOrderById(ORDER_ID));
        assertNull(orderUtils.getOrderById(2L));
    }
}
