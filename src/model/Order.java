package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {

    private final UUID id;
    private final Customer customer;
    private final LocalDateTime creationDate;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private final List<OrderItem> items;

    public Order(UUID id, Customer customer, LocalDateTime creationDate) {
        this.id = id;
        this.customer = customer;
        this.creationDate = creationDate;
        this.items = new ArrayList<>();

    }
}
