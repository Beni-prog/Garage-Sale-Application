package com.endava.garagesale.entity;

import com.endava.garagesale.request.OrderRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.endava.garagesale.entity.Order.Status.PAYED;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptFactoryTest {

    @Test
    public void getReceipt() {
        List<Product> products = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        List<Receipt> receipts = new ArrayList<>();

        User user = buildUser();
        users.add(user);

        Card card = buildCard();

        for (Product product : getProducts()) {
            products.add(product);
            names.add(product.getName());
            ids.add(product.getId());
        }

        OrderRequest orderRequest = buildOrderRequest(ids, user, card);
        Order order = Order.OrderBuilder.anOrder()
                .withId(1L)
                .withCreatedDate(new Date())
                .withProductList(products)
                .withStatus(PAYED)
                .withTotalAmount(600)
                .withUser(user)
                .build();
        orders.add(order);

        Optional<User> userOptional = Optional.ofNullable(users.get(0));
        Receipt receipt = ReceiptFactory.getReceipt(user, card.getNumber(), card.getExpDate(),
                names, order.getTotalAmount(), order.getStatus());

        ReceiptSuccessfulOrder receiptSuccessfulOrder = new ReceiptSuccessfulOrder(user, card.getNumber(), names, 100, PAYED);

        assertEquals(PAYED, receipt.getStatus());

        assertEquals(card.getNumber(), receiptSuccessfulOrder.getCardNumber());
        assertEquals(user, receiptSuccessfulOrder.getUser());
        assertEquals(names, receiptSuccessfulOrder.getPurchasedProducts());
        assertEquals(100, receiptSuccessfulOrder.getSum());
        assertEquals(PAYED, receiptSuccessfulOrder.getStatus());

        ReceiptInvalidUserAccount receiptInvalidUserAccount = new ReceiptInvalidUserAccount(user);


        assertEquals(PAYED, order.getStatus());
        assertEquals(600, order.getTotalAmount());
        assertEquals(user, order.getUser());
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

    public User buildUser() {
        User user = new User("User1", "test@test.com", User.AccountStatus.ACTIVE);
        user.setId(1L);

        return user;
    }

    public Card buildCard() {
        return new Card("Beni", "1111222233334444", YearMonth.of(2022, 8), "123");
    }

    public OrderRequest buildOrderRequest(List<Long> ids, User user, Card card) {
        return new OrderRequest(ids, user.getId(), card);
    }

    public Order buildOrder(List<Product> productList, Date createdDate, Order.Status status, User user, double totalAmount) {
        return new Order(productList, createdDate, status, user, totalAmount);
    }
}
