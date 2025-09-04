package service;

import model.Customer;
import model.Order;
import model.OrderStatus;
import util.IdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private final IOrderRepository orderRepository;
    private final ICustomerRepository customerRepository;
    private final IProductRepository productRepository;
    private final NotificationService notificationService;
    private final IdGenerator idGenerator;

    public OrderService(IOrderRepository orderRepository,
                        ICustomerRepository customerRepository,
                        IProductRepository productRepository,
                        NotificationService notificationService,
                        IdGenerator idGenerator) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.notificationService = notificationService;
        this.idGenerator = idGenerator;
    }

    public Order createOrder(String customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new IllegalArgumentException("Cliente com ID " + customerId + " não encontrado.");
        }
        Customer customer = customerOptional.get();

        String orderId = idGenerator.generateId();
        LocalDateTime creationDate = LocalDateTime.now();
        Order order = new Order(orderId, customer, creationDate, OrderStatus.OPEN, null, new ArrayList<>());
        orderRepository.save(order);

        System.out.println("--------------------------------------------------");
        System.out.println("Pedido (ID: " + order.getId() + ") criado para o cliente '" + customer.getName() + "'. Status: " + order.getStatus());
        System.out.println("--------------------------------------------------");
        return order;
    }

    public void addItemToOrder(String orderId, String productId, int quantity, double saleValue) {}

    public void removeItemFromOrder(String orderId, String productId) {}

    public void updateItemQuantity(String orderId, String productId, int newQuantity) {}

    public void finalizeOrder(String orderId) {}

    public void processPayment(String orderId) {}

    public void registerDelivery(String orderId) {}

    public Optional<Order> findOrderById(String id) {}

    public List<Order> listAllOrders() {}



}
