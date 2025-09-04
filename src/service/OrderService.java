package service;

import model.Order;
import util.IdGenerator;

import java.util.Optional;

public class OrderService {
    private final IOrderRepository orderRepository;
    private final ICustomerRepository customerRepository;
    private final IProductRepository productRepository;
    private final NotificationService notificationService;
    private final IdGenerator idGenerator;

    public OrderService(IOrderRepository orderRepository, ICustomerRepository customerRepository, IProductRepository productRepository, NotificationService notificationService, IdGenerator idGenerator, IOrderRepository orderRepository1, ICustomerRepository customerRepository1, IProductRepository productRepository1, NotificationService notificationService1, IdGenerator idGenerator1) {
        this.orderRepository = orderRepository1;
        this.customerRepository = customerRepository1;
        this.productRepository = productRepository1;
        this.notificationService = notificationService1;
        this.idGenerator = idGenerator1;
    }

    public Order createOrder(String customerId) {}

    public void addItemToOrder(String orderId, String productId, int quantity, double saleValue) {}

    public void removeItemFromOrder(String orderId, String productId) {}

    public void updateItemQuantity(String orderId, String productId, int newQuantity) {}

    public void finalizeOrder(String orderId) {}

    public void processPayment(String orderId) {}

    public void registerDelivery(String orderId) {}

    public Optional<Order> findOrderById(String id) {}

    public List<Order> listAllOrders() {}



}
