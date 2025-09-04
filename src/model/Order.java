package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {

    private final String id;
    private final Customer customer;
    private final LocalDateTime creationDate;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private final List<OrderItem> items;

    public Order(
            String id,
            Customer customer,
            LocalDateTime creationDate,
            OrderStatus status,
            PaymentStatus paymentStatus,
            List<OrderItem> items
    ) {
        this.id = id;
        this.customer = customer;
        this.creationDate = creationDate;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Customer getCustomer() {
        return customer;
    }
}

