package repository;

import model.*;
import util.FileUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderFileRepository implements IOrderRepository {
    private final String filePath;
    private final FileUtils fileUtils;
    private final ICustomerRepository customerRepository;
    private final IProductRepository productRepository;

    private static final String FIELD_DELIMITER = ";";
    private static final String ITEM_DELIMITER = "\\|";
    private static final String ITEM_FIELD_DELIMITER = ",";

    private static final int ORDER_ID_INDEX = 0;
    private static final int CUSTOMER_ID_INDEX = 1;
    private static final int CREATION_DATE_INDEX = 2;
    private static final int ORDER_STATUS_INDEX = 3;
    private static final int PAYMENT_STATUS_INDEX = 4;
    private static final int ITEMS_STRING_INDEX = 5;

    private static final int PRODUCT_ID_ITEM_INDEX = 0;
    private static final int QUANTITY_ITEM_INDEX = 1;
    private static final int SALE_VALUE_ITEM_INDEX = 2;

    public OrderFileRepository(String filePath, FileUtils fileUtils,
                               ICustomerRepository customerRepository,
                               IProductRepository productRepository) {
        this.filePath = filePath;
        this.fileUtils = fileUtils;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void save(Order order) {
        fileUtils.writeLine(filePath, convertOrderToString(order), true);
    }

    @Override
    public Optional<Order> findById(String id) {
        return loadOrders().stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Order> findAll() {
        return loadOrders();
    }

    @Override
    public void update(Order orderToUpdate) {
        List<Order> orders = loadOrders();
        boolean orderFound = false;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equals(orderToUpdate.getId())) {
                orders.set(i, orderToUpdate);
                orderFound = true;
                break;
            }
        }
        if (orderFound) {
            writeOrders(orders);
        } else {
            System.err.println("Pedido com ID " + orderToUpdate.getId() + " não encontrado para atualização.");
        }
    }

    private List<Order> loadOrders() {
        List<Order> orders = new ArrayList<>();
        List<String> lines = fileUtils.readFromFile(filePath);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                try {
                    orders.add(convertStringToOrder(line));
                } catch (Exception e) {
                    System.err.println("Erro ao analisar a linha do pedido: " + line + " - " + e.getMessage());
                }
            }
        }
        return orders;
    }

    private void writeOrders(List<Order> orders) {
        List<String> lines = orders.stream()
                .map(this::convertOrderToString)
                .collect(Collectors.toList());
        fileUtils.writeAllToFile(filePath, lines);
    }

    private String convertOrderToString(Order order) {
        String itemsString = order.getItems().stream()
                .map(item -> String.join(ITEM_FIELD_DELIMITER,
                        item.getProduct().getId(),
                        String.valueOf(item.getQuantity()),
                        item.getSaleValue().toPlainString()))
                .collect(Collectors.joining(ITEM_DELIMITER));

        return String.join(FIELD_DELIMITER,
                order.getId(),
                order.getCustomer().getId(),
                order.getCreationDate().toString(),
                order.getStatus().name(),
                order.getPaymentStatus().name(),
                itemsString);
    }

    private Order convertStringToOrder(String line) {
        String[] parts = line.split(FIELD_DELIMITER);

        if (parts.length < 5) {
            throw new IllegalArgumentException("Formato de linha de pedido inválido (poucos campos): " + line);
        }

        String orderId = parts[ORDER_ID_INDEX];
        String customerId = parts[CUSTOMER_ID_INDEX];
        LocalDateTime creationDate = LocalDateTime.parse(parts[CREATION_DATE_INDEX]);
        OrderStatus orderStatus = OrderStatus.valueOf(parts[ORDER_STATUS_INDEX]);
        PaymentStatus paymentStatus = PaymentStatus.valueOf(parts[PAYMENT_STATUS_INDEX]);

        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        Customer customer = customerOpt.orElseThrow(() ->
                new IllegalStateException("Cliente não encontrado para pedido com ID: " + customerId));

        List<OrderItem> items = new ArrayList<>();
        if (parts.length > ITEMS_STRING_INDEX && !parts[ITEMS_STRING_INDEX].isEmpty()) {
            String[] itemStrings = parts[ITEMS_STRING_INDEX].split(ITEM_DELIMITER);
            for (String itemString : itemStrings) {
                String[] itemParts = itemString.split(ITEM_FIELD_DELIMITER);
                if (itemParts.length == 3) {
                    String productId = itemParts[PRODUCT_ID_ITEM_INDEX];
                    int quantity = Integer.parseInt(itemParts[QUANTITY_ITEM_INDEX]);
                    BigDecimal saleValue = new BigDecimal(itemParts[SALE_VALUE_ITEM_INDEX]);

                    Optional<Product> productOpt = productRepository.findById(productId);
                    Product product = productOpt.orElseThrow(() ->
                            new IllegalStateException("Produto não encontrado para o item do pedido com ID: " + productId));

                    items.add(new OrderItem(product, quantity, saleValue));
                } else {
                    System.err.println("Formato de item de pedido inválido: " + itemString + " na linha: " + line);
                }
            }
        }

        Order order = new Order(orderId, customer, creationDate, orderStatus, paymentStatus, items);
        return order;
    }
}
