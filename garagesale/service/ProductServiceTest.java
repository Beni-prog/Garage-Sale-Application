package com.endava.garagesale.service;

import com.endava.garagesale.entity.Product;
import com.endava.garagesale.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    ProductRepository productRepository;

    ProductService productService;

    @Before
    public void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    public void getAvailableProducts() {

        when(productRepository.findAll()).thenReturn(getThreeProducts());

        assertEquals(2, productService.getAvailableProducts().size());
    }

    @Test
    public void getProductById() {
        when(productRepository.findAll()).thenReturn(getThreeProducts());

        assertEquals(getThreeProducts().iterator().next(), productService.getProductById(1L));
        assertNull(productService.getProductById(4L));
    }

    public Iterable<Product> getThreeProducts() {
        Product product1 = new Product(1L, "001", "Product1", 100, new ArrayList<>(), 10, Product.Category.LAPTOPS);
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
