package com.endava.garagesale.utils;

import com.endava.garagesale.entity.Order;
import com.endava.garagesale.entity.Product;
import com.endava.garagesale.entity.User;
import com.endava.garagesale.repository.OrderRepository;
import com.endava.garagesale.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ProductUtilsTest {
    private ProductUtils productUtils;

    private ProductRepository productRepository;
    private OrderRepository orderRepositoryOrderUtilsProductUtils;
    private OrderUtils orderUtilsProductUtils;

    @Before
    public void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        orderRepositoryOrderUtilsProductUtils = Mockito.mock(OrderRepository.class);
        orderUtilsProductUtils = new OrderUtils(orderRepositoryOrderUtilsProductUtils);
        orderUtilsProductUtils = Mockito.mock(OrderUtils.class);
        productUtils = new ProductUtils(productRepository, orderUtilsProductUtils);
    }

    @Test
    public void isFromTheSameCategoryTest() {
        List<Product> products = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (Product product : getProducts()) {
            products.add(product);
            ids.add(product.getId());
        }
        doReturn(products).when(productRepository).findAll();

        assertFalse(productUtils.isFromTheSameCategory(ids));
    }

    @Test
    public void getProductIdsTest() {
        List<Product> products = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (Product product : getProducts()) {
            products.add(product);
            ids.add(product.getId());
        }
        Order order = new Order(products, new Date(), Order.Status.PAYED, new User(), 0.00);

        doReturn(order).when(orderUtilsProductUtils).getOrderById(order.getId());

        assertEquals(ids, productUtils.getProductIds(order.getId()));
    }

    @Test
    public void getPurchasedProductsTest() {
        List<Product> products = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (Product product : getProducts()) {
            products.add(product);
            ids.add(product.getId());
        }
        doReturn(products).when(productRepository).findAll();

        assertEquals(products, productUtils.getPurchasedProducts(ids));
    }

    @Test
    public void getPurchasedProductsNamesTest() {
        List<Product> products = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (Product product : getProducts()) {
            products.add(product);
            ids.add(product.getId());
            names.add(product.getName());
        }
        Order order = new Order(products, new Date(), Order.Status.PAYED, new User(), 0.00);
        order.setId(1L);

        doReturn(products).when(productRepository).findAll();
        doReturn(order).when(orderUtilsProductUtils).getOrderById(order.getId());

        assertEquals(names, productUtils.getPurchasedProductsNames(order.getId()));
    }

    @Test
    public void getTotalSumTest() {
        List<Product> products = new ArrayList<>();
        for (Product product : getProducts()) {
            products.add(product);
        }

        List<Long> ids = new ArrayList<>();
        ids.add(products.get(0).getId());

        doReturn(products).when(productRepository).findAll();

        assertEquals(100.00, productUtils.getTotalSum(ids));
    }

    public Iterable<Product> getProducts() {
        Product product1 = new Product(1L, "001", "Product1", 100.00, new ArrayList<>(), 10, Product.Category.LAPTOPS);
        Product product2 = new Product(2L, "002", "Product2", 100.00, new ArrayList<>(), 10, Product.Category.TABLETS);
        Product product3 = new Product(3L, "003", "Product3", 100.00, new ArrayList<>(), 10, Product.Category.PHONES);
        Product product4 = new Product(4L, "004", "Product4", 100.00, new ArrayList<>(), 10, Product.Category.TVs);
        Product product5 = new Product(5L, "005", "Product5", 100.00, new ArrayList<>(), 10, Product.Category.PROJECTORS);
        Product product6 = new Product(6L, "006", "Product6", 100.00, new ArrayList<>(), 10, Product.Category.MONITORS);

        List<Product> products = new ArrayList<>();

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);

        Iterable<Product> productIterable = products;

        return productIterable;
    }
}
