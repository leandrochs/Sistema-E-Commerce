package service;

import model.*;
import util.IdGenerator;

import java.math.BigDecimal;
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

    public void addItemToOrder(String orderId, String productId, int quantity, BigDecimal saleValue) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new IllegalArgumentException("Pedido com ID " + orderId + " não encontrado.");
        }
        Order order = orderOptional.get();

        if (order.getStatus() != OrderStatus.OPEN) {
            throw new IllegalArgumentException("Não é possível adicionar itens a um pedido que não esteja 'OPEN'. Status atual: " + order.getStatus());
        }

        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("Produto com ID " + productId + " não encontrado.");
        }
        Product product = productOptional.get();

        Optional<OrderItem> existingItem = order.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            OrderItem itemToUpdate = existingItem.get();
            itemToUpdate.setQuantity(itemToUpdate.getQuantity() + quantity);
            System.out.println("Quantidade do item '" + product.getName() + "' atualizada no pedido " + order.getId());
        } else {
            OrderItem newItem = new OrderItem(product, quantity, saleValue);
            order.getItems().add(newItem);
            System.out.println("Item '" + product.getName() + "' adicionado ao pedido " + order.getId());
        }

        orderRepository.update(order);
        System.out.println("--------------------------------------------------");
        System.out.println("Item '" + product.getName() + "' adicionado/atualizado no pedido (ID: " + order.getId() + "). Quantidade: " + quantity + ", Valor de Venda: " + saleValue);
        System.out.println("--------------------------------------------------");
    }

    public void removeItemFromOrder(String orderId, String productId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new IllegalArgumentException("Pedido com ID " + orderId + " não encontrado.");
        }
        Order order = orderOptional.get();

        if (order.getStatus() != OrderStatus.OPEN) {
            throw new IllegalArgumentException("Não é possível remover itens de um pedido que não esteja 'OPEN'. Status atual: " + order.getStatus());
        }

        boolean removed = order.getItems().removeIf(item -> item.getProduct().getId().equals(productId));

        if (removed) {
            orderRepository.update(order);
            System.out.println("--------------------------------------------------");
            System.out.println("Item com ID '" + productId + "' removido do pedido (ID: " + order.getId() + ").");
            System.out.println("--------------------------------------------------");
        } else {
            System.out.println("--------------------------------------------------");
            System.out.println("Item com ID '" + productId + "' não encontrado no pedido (ID: " + order.getId() + "). Nenhuma alteração.");
            System.out.println("--------------------------------------------------");
        }
    }

    public void updateItemQuantity(String orderId, String productId, int newQuantity) {}

    public void finalizeOrder(String orderId) {}

    public void processPayment(String orderId) {}

    public void registerDelivery(String orderId) {}

    public Optional<Order> findOrderById(String id) {}

    public List<Order> listAllOrders() {}



}
