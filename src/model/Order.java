package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final Customer customer;
    private final LocalDateTime creationDate;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private final List<OrderItem> items;

    public Order(UUID id, Customer customer, LocalDateTime creationDate) {
        this.id = id; = UUID.randomUUID();
        this.customer = customer;
        this.creationDate = LocalDateTime.now();
        this.status = OrderStatus.OPEN;
        this.paymentStatus = PaymentStatus.PENDING_PAYMENT;
        this.items = new ArrayList<>();

    }
public Order(UUID id, Customer customer, LocalDateTime creationDate, OrderStatus status, PaymentStatus paymentStatus, List<OrderItem> items) {
        this.id = id;
        this.customer = customer;
        this.creationDate = creationDate;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.items = items;
    }

    public UUID getId() {
    return id;}
    public Customer getCustomer() {
        return customer;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public List<OrderItem> getItems() {
        return items;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;

    }
}


