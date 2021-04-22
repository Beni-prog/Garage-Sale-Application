package com.endava.garagesale.utils;

import com.endava.garagesale.entity.Product;
import com.endava.garagesale.repository.OrderRepository;
import com.endava.garagesale.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.endava.garagesale.entity.Product.Category.LAPTOPS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ProductChecksTest {
    private ProductChecks productChecks;

    private ProductUtils productUtils;

    private ProductRepository productRepositoryProductUtils;
    private OrderRepository orderRepositoryOrderUtilsProductUtils;
    private OrderUtils orderUtilsProductUtils;

    @Before
    public void setUp() {
        productRepositoryProductUtils = Mockito.mock(ProductRepository.class);

        orderRepositoryOrderUtilsProductUtils = Mockito.mock(OrderRepository.class);

        orderUtilsProductUtils = new OrderUtils(orderRepositoryOrderUtilsProductUtils);

        productUtils = new ProductUtils(productRepositoryProductUtils, orderUtilsProductUtils);

        productUtils = Mockito.mock(ProductUtils.class);
        productChecks = new ProductChecks(productUtils);
    }

    @Test
    public void testProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setCategory(LAPTOPS);
        product.setCode("111");
        product.setPrice(100);
        product.setIssues(new ArrayList<>());
        product.setName("Product");
        product.setNrOfItems(10);

        assertEquals("111", product.getCode());
        assertEquals(1L, product.getId());
        assertEquals("Product", product.getName());
        assertEquals(10, product.getNrOfItems());
        assertEquals(100, product.getPrice());
        assertEquals(LAPTOPS, product.getCategory());
    }

    @Test
    public void productExists() {
        List<Product> products = new ArrayList<>();
        for (Product product : getProducts()) {
            products.add(product);
        }
        doReturn(products.get(0)).when(productUtils).getProductById(1L);

        assertTrue(productChecks.productExistsOnStock(1L));
    }

    @Test
    public void productDoesNotExist() {
        List<Product> products = new ArrayList<>();
        for (Product product : getProducts()) {
            products.add(product);
        }
        doReturn(products.get(1)).when(productUtils).getProductById(2L);

        assertFalse(productChecks.productExistsOnStock(2L));
    }

    public Iterable<Product> getProducts() {
        Product product1 = new Product(1L, "001", "Product1", 100, new ArrayList<>(), 10, LAPTOPS);
        Product product2 = new Product(2L, "002", "Product2", 100, new ArrayList<>(), 0, Product.Category.TABLETS);
        Product product3 = new Product(3L, "003", "Product3", 100, new ArrayList<>(), 10, Product.Category.PHONES);

        List<Product> products = new ArrayList<>();

        products.add(product1);
        products.add(product2);
        products.add(product3);

        Iterable<Product> productIterable = products;

        return productIterable;
    }

}
