package com.endava.garagesale.service;

import com.endava.garagesale.entity.*;
import com.endava.garagesale.repository.OrderRepository;
import com.endava.garagesale.repository.ProductRepository;
import com.endava.garagesale.repository.ReceiptRepository;
import com.endava.garagesale.repository.UserRepository;
import com.endava.garagesale.request.OrderRequest;
import com.endava.garagesale.utils.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    private OrderService orderService;

    private ReceiptRepository receiptRepository;
    private ProductUtils productUtils;
    private UserRepository userRepository;
    private OrderChecksService orderChecksService;
    private UserChecksService userChecksService;

    private CardChecks cardChecks;
    private UserChecks userChecks;
    private ProductChecks productChecks;
    private OrderUtils orderUtils;

    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    private BuildAndPersist buildAndPersist;

    private static final String INVALID_CARD_NUMBER = "11112222333344";
    private static final String VALID_CARD_NUMBER = "1111222233334444";
    private static final YearMonth VALID_CARD_DATE = YearMonth.of(2022, 6);
    private static final YearMonth INVALID_CARD_DATE = YearMonth.of(2020, 6);

    @Before
    public void setUp() {
        receiptRepository = Mockito.mock(ReceiptRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        orderRepository = Mockito.mock(OrderRepository.class);
        productRepository = Mockito.mock(ProductRepository.class);

        cardChecks = new CardChecks();
        userChecks = new UserChecks();
        orderUtils = new OrderUtils(orderRepository);
        productUtils = new ProductUtils(productRepository, orderUtils);
        productUtils = Mockito.mock(ProductUtils.class);

        userChecksService = new UserChecksService();
        productChecks = new ProductChecks(productUtils);
        productChecks = Mockito.mock(ProductChecks.class);
        orderChecksService = new OrderChecksService(cardChecks, userChecks, productChecks, productUtils);

        buildAndPersist = new BuildAndPersist(userRepository, receiptRepository, orderRepository, productUtils);
        orderService = new OrderServiceImpl(receiptRepository, productUtils, userRepository, orderChecksService, userChecksService, buildAndPersist);
    }

    @Test()
    public void createOrderInvalidCardNumber() {
        when(userRepository.existsById(any())).thenReturn(true);
        when(userRepository.findById(any())).thenReturn(Optional.of(buildUser()));
        when(productUtils.getPurchasedProducts(any())).thenReturn(new ArrayList<>());
        Order order = buildOrder(null, new Date(), Order.Status.INVALID_CARD_NUMBER, buildUser(), 10);
        order.setId(1L);
        when(orderRepository.save(any())).thenReturn(order);
        when(productUtils.getPurchasedProductsNames(any())).thenReturn(new ArrayList<>());
        Receipt receipt = orderService.createOrder(buildOrderRequest(new ArrayList<>(), buildUser(), buildInvalidNumberCard()), buildInvalidNumberCard().getNumber());

        assertTrue(receipt instanceof ReceiptInvalidCardNumber);
        assertEquals(Order.Status.INVALID_CARD_NUMBER, receipt.getStatus());
        assertEquals(INVALID_CARD_NUMBER, ((ReceiptInvalidCardNumber) receipt).getCardNumber());
    }

    @Test
    public void createOrderInvalidCardDate() {
        when(userRepository.existsById(any())).thenReturn(true);
        when(userRepository.findById(any())).thenReturn(Optional.of(buildUser()));
        when(productUtils.getPurchasedProducts(any())).thenReturn(new ArrayList<>());
        Order order = buildOrder(null, new Date(), Order.Status.CARD_DATE_EXPIRED, buildUser(), 10);
        order.setId(1L);
        when(orderRepository.save(any())).thenReturn(order);
        when(productUtils.getPurchasedProductsNames(any())).thenReturn(new ArrayList<>());
        Receipt receipt = orderService.createOrder(buildOrderRequest(new ArrayList<>(), buildUser(), buildInvalidDateCard()), buildInvalidDateCard().getNumber());

        assertTrue(receipt instanceof ReceiptInvalidCardDate);
        assertEquals(Order.Status.CARD_DATE_EXPIRED, receipt.getStatus());
        assertEquals(INVALID_CARD_DATE, ((ReceiptInvalidCardDate) receipt).getExpDate());
    }

    @Test
    public void createOrderUserAccountBlocked() {
        when(userRepository.existsById(any())).thenReturn(true);
        when(userRepository.findById(any())).thenReturn(Optional.of(buildInvalidUser()));
        when(productUtils.getPurchasedProducts(any())).thenReturn(new ArrayList<>());
        Order order = buildOrder(null, new Date(), Order.Status.USER_ACCOUNT_BLOCKED, buildInvalidUser(), 10);
        order.setId(1L);
        when(orderRepository.save(any())).thenReturn(order);
        when(productUtils.getPurchasedProductsNames(any())).thenReturn(new ArrayList<>());
        Receipt receipt = orderService.createOrder(buildOrderRequest(new ArrayList<>(), buildInvalidUser(), buildValidCard()), buildValidCard().getNumber());

        assertTrue(receipt instanceof ReceiptInvalidUserAccount);
        assertEquals(Order.Status.USER_ACCOUNT_BLOCKED, receipt.getStatus());
        assertEquals(buildInvalidUser(), ((ReceiptInvalidUserAccount) receipt).getUser());
    }

    @Test
    public void createOrderProductsFromTheSameCategory() {
        when(userRepository.existsById(any())).thenReturn(true);
        when(userRepository.findById(any())).thenReturn(Optional.of(buildUser()));
        List<Product> products = new ArrayList<>();
        products.add(getProducts().get(0));
        products.add(new Product(7L, "007", "Laptop HP", 100, new ArrayList<>(), 10, Product.Category.LAPTOPS));
        when(productUtils.getPurchasedProducts(any())).thenReturn(products);
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(7L);
        when(productUtils.isFromTheSameCategory(ids)).thenReturn(true);
        List<String> names = new ArrayList<>();
        names.add(products.get(0).getName());
        names.add(products.get(1).getName());
        Order order = buildOrder(products, new Date(), Order.Status.PRODUCT_FROM_THE_SAME_CATEGORY, buildUser(), 200);
        order.setId(1L);
        when(orderRepository.save(any())).thenReturn(order);
        when(productUtils.getPurchasedProductsNames(any())).thenReturn(names);
        Receipt receipt = orderService.createOrder(buildOrderRequest(ids, buildUser(), buildValidCard()), buildValidCard().getNumber());

        assertTrue(receipt instanceof ReceiptSameCategory);
        assertEquals(Order.Status.PRODUCT_FROM_THE_SAME_CATEGORY, receipt.getStatus());
        assertEquals(names, ((ReceiptSameCategory) receipt).getPurchasedProducts());
    }

    @Test
    public void createOrderProductOutOfStock() {
        when(userRepository.existsById(any())).thenReturn(true);
        when(userRepository.findById(any())).thenReturn(Optional.of(buildUser()));
        List<Product> products = new ArrayList<>();
        products.add(getProducts().get(1));
        products.add(new Product(7L, "007", "Laptop HP", 100, new ArrayList<>(), 10, Product.Category.LAPTOPS));
        when(productUtils.getPurchasedProducts(any())).thenReturn(products);
        List<Long> ids = new ArrayList<>();
        ids.add(2L);
        ids.add(7L);
        List<String> names = new ArrayList<>();
        names.add(products.get(0).getName());
        names.add(products.get(1).getName());
        Order order = buildOrder(products, new Date(), Order.Status.PRODUCT_OUT_OF_STOCK, buildUser(), 200);
        order.setId(1L);
        when(userRepository.findById(any())).thenReturn(Optional.of(buildUser()));
        when(orderRepository.save(any())).thenReturn(order);
        when(productUtils.getPurchasedProductsNames(any())).thenReturn(names);

        Receipt receipt = orderService.createOrder(buildOrderRequest(ids, buildUser(), buildValidCard()), buildValidCard().getNumber());

        assertTrue(receipt instanceof ReceiptProductOutOfStock);
        assertEquals(Order.Status.PRODUCT_OUT_OF_STOCK, receipt.getStatus());
        assertEquals(names, ((ReceiptProductOutOfStock) receipt).getPurchasedProducts());
        assertEquals(names, ((ReceiptProductOutOfStock) receipt).getPurchasedProducts());
    }

    @Test
    public void createOrderSuccessful() {
        when(userRepository.existsById(any())).thenReturn(true);
        when(userRepository.findById(any())).thenReturn(Optional.of(buildUser()));
        List<Product> products = getProducts();
        products.removeIf(product -> product.getNrOfItems() == 0);
        when(productUtils.getPurchasedProducts(any())).thenReturn(products);
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        ids.add(4L);
        ids.add(5L);
        ids.add(6L);
        List<String> names = new ArrayList<>();
        names.add(products.get(0).getName());
        names.add(products.get(1).getName());
        names.add(products.get(2).getName());
        names.add(products.get(3).getName());
        names.add(products.get(4).getName());
        when(productChecks.productExistsOnStock(any())).thenReturn(true);
        Order order = buildOrder(products, new Date(), Order.Status.PAYED, buildUser(), 600);
        order.setId(1L);
        when(orderRepository.save(any())).thenReturn(order);
        when(productUtils.getPurchasedProductsNames(any())).thenReturn(names);
        Receipt receipt = orderService.createOrder(buildOrderRequest(ids, buildUser(), buildValidCard()), buildValidCard().getNumber());

        assertTrue(receipt instanceof ReceiptSuccessfulOrder);
        assertEquals(Order.Status.PAYED, receipt.getStatus());
        assertEquals(600, ((ReceiptSuccessfulOrder) receipt).getSum());
        assertEquals(names, ((ReceiptSuccessfulOrder) receipt).getPurchasedProducts());
        assertEquals(buildUser(), ((ReceiptSuccessfulOrder) receipt).getUser());
        assertEquals(buildValidCard().getNumber(), ((ReceiptSuccessfulOrder) receipt).getCardNumber());
    }

    public List<Product> getProducts() {
        Product product1 = new Product(1L, "001", "Product1", 100.00, new ArrayList<>(), 10, Product.Category.LAPTOPS);
        Product product2 = new Product(2L, "002", "Product2", 100.00, new ArrayList<>(), 0, Product.Category.TABLETS);
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

        return products;
    }

    public User buildUser() {
        User user = new User("User1", "test@test.com", com.endava.garagesale.entity.User.AccountStatus.ACTIVE);
        user.setId(1L);

        return user;
    }

    public User buildInvalidUser() {

        User user = new User("User1", "test@test.com", com.endava.garagesale.entity.User.AccountStatus.BLOCKED);
        user.setId(1L);

        return user;
    }

    public Card buildInvalidNumberCard() {
        return new Card("Beni", INVALID_CARD_NUMBER, VALID_CARD_DATE, "123");
    }

    public Card buildInvalidDateCard() {
        return new Card("Beni", VALID_CARD_NUMBER, INVALID_CARD_DATE, "123");
    }

    public Card buildValidCard() {
        return new Card("Beni", VALID_CARD_NUMBER, VALID_CARD_DATE, "123");
    }

    public OrderRequest buildOrderRequest(List<Long> ids, User user, Card card) {
        return new OrderRequest(ids, user.getId(), card);
    }

    public Order buildOrder(List<Product> productList, Date createdDate, Order.Status status, User user, double totalAmount) {
        return new Order(productList, createdDate, status, user, totalAmount);
    }
}
