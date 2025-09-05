package application;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import model.Customer;
import model.Order;
import model.Product;

import repository.CustomerFileRepository;
import repository.ICustomerRepository;
import repository.IOrderRepository;
import repository.IProductRepository;
import repository.OrderFileRepository;
import repository.ProductFileRepository;

import service.CustomerService;
import service.NotificationService;
import service.OrderService;
import service.ProductService;

import util.FileUtils;
import util.IdGenerator;

public class Main {

    public static void main(String[] args) {
        FileUtils fileUtils = new FileUtils();
        IdGenerator idGenerator = new IdGenerator();

        String customerFilePath = "resources/data/customers.txt";
        String productFilePath = "resources/data/products.txt";
        String orderFilePath = "resources/data/orders.txt";

        ICustomerRepository customerRepository = new CustomerFileRepository(customerFilePath, fileUtils);
        IProductRepository productRepository = new ProductFileRepository(productFilePath, fileUtils);

        IOrderRepository orderRepository = new OrderFileRepository(orderFilePath, fileUtils, customerRepository, productRepository);

        NotificationService notificationService = new NotificationService();

        CustomerService customerService = new CustomerService(customerRepository, idGenerator);
        ProductService productService = new ProductService(productRepository, idGenerator);
        OrderService orderService = new OrderService(orderRepository, customerRepository, productRepository, notificationService, idGenerator);

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n--- Menu Principal Ada Commerce ---");
            System.out.println("1. Gerenciar Clientes");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("3. Gerenciar Pedidos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        handleCustomerMenu(scanner, customerService);
                        break;
                    case 2:
                        handleProductMenu(scanner, productService);
                        break;
                    case 3:
                        handleOrderMenu(scanner, orderService, customerService, productService);
                        break;
                    case 0:
                        System.out.println("Saindo da aplicação. Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
                option = -1;
            }

        } while (option != 0);

        scanner.close();
    }

    private static void handleCustomerMenu(Scanner scanner, CustomerService customerService) {
        int option;
        do {
            System.out.println("\n--- Gerenciar Clientes ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Todos os Clientes");
            System.out.println("3. Buscar Cliente por ID");
            System.out.println("4. Atualizar Cliente");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        System.out.print("Nome do Cliente: ");
                        String name = scanner.nextLine();
                        System.out.print("Documento de Identificação (CPF): ");
                        String document = scanner.nextLine();
                        customerService.registerCustomer(name, document);
                        System.out.println("Cliente cadastrado com sucesso!");
                        break;
                    case 2:
                        List<Customer> customers = customerService.listAllCustomers();
                        if (customers.isEmpty()) {
                            System.out.println("Nenhum cliente cadastrado.");
                        } else {
                            customers.forEach(System.out::println);
                        }
                        break;
                    case 3:
                        System.out.print("ID do Cliente: ");
                        String id = scanner.nextLine();
                        Optional<Customer> foundCustomer = customerService.findCustomerById(id);
                        foundCustomer.ifPresentOrElse(
                                System.out::println,
                                () -> System.out.println("Cliente não encontrado.")
                        );
                        break;
                    case 4:
                        System.out.print("ID do Cliente a ser atualizado: ");
                        String updateId = scanner.nextLine();
                        Optional<Customer> customerToUpdateOpt = customerService.findCustomerById(updateId);
                        if (customerToUpdateOpt.isPresent()) {
                            Customer customerToUpdate = customerToUpdateOpt.get();
                            System.out.print("Novo Nome (deixe em branco para não alterar): ");
                            String newName = scanner.nextLine();
                            if (!newName.isEmpty()) {
                                customerToUpdate.setName(newName);
                            }
                            System.out.print("Novo Documento de Identificação (deixe em branco para não alterar): ");
                            String newDocument = scanner.nextLine();
                            if (!newDocument.isEmpty()) {
                                customerToUpdate.setIdentificationDocument(newDocument);
                            }
                            customerService.updateCustomer(customerToUpdate);
                            System.out.println("Cliente atualizado com sucesso!");
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
                option = -1;
            } catch (Exception e) {
                System.err.println("Erro ao gerenciar cliente: " + e.getMessage());
                option = -1;
            }
        } while (option != 0);
    }

    private static void handleProductMenu(Scanner scanner, ProductService productService) {
        int option;
        do {
            System.out.println("\n--- Gerenciar Produtos ---");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Todos os Produtos");
            System.out.println("3. Buscar Produto por ID");
            System.out.println("4. Atualizar Produto");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        System.out.print("Nome do Produto: ");
                        String name = scanner.nextLine();
                        System.out.print("Descrição do Produto: ");
                        String description = scanner.nextLine();
                        System.out.print("Valor do Produto (ex: 12.99): ");
                        BigDecimal value = scanner.nextBigDecimal();
                        scanner.nextLine();
                        productService.registerProduct(name, description, value);
                        System.out.println("Produto cadastrado com sucesso!");
                        break;
                    case 2:
                        List<Product> products = productService.listAllProducts();
                        if (products.isEmpty()) {
                            System.out.println("Nenhum produto cadastrado.");
                        } else {
                            products.forEach(System.out::println);
                        }
                        break;
                    case 3:
                        System.out.print("ID do Produto: ");
                        String id = scanner.nextLine();
                        Optional<Product> foundProduct = productService.findProductById(id);
                        foundProduct.ifPresentOrElse(
                                System.out::println,
                                () -> System.out.println("Produto não encontrado.")
                        );
                        break;
                    case 4:
                        System.out.print("ID do Produto a ser atualizado: ");
                        String updateId = scanner.nextLine();
                        Optional<Product> productToUpdateOpt = productService.findProductById(updateId);
                        if (productToUpdateOpt.isPresent()) {
                            Product productToUpdate = productToUpdateOpt.get();
                            System.out.print("Novo Nome (deixe em branco para não alterar): ");
                            String newName = scanner.nextLine();
                            if (!newName.isEmpty()) {
                                productToUpdate.setName(newName);
                            }
                            System.out.print("Nova Descrição (deixe em branco para não alterar): ");
                            String newDescription = scanner.nextLine();
                            if (!newDescription.isEmpty()) {
                                productToUpdate.setDescription(newDescription);
                            }
                            System.out.print("Novo Valor (deixe em branco ou 0 para não alterar): ");
                            String newValueStr = scanner.nextLine();
                            if (!newValueStr.isEmpty() && !newValueStr.equals("0")) {
                                try {
                                    BigDecimal newValue = new BigDecimal(newValueStr);
                                    productToUpdate.setValue(newValue);
                                } catch (NumberFormatException e) {
                                    System.out.println("Valor inválido, mantendo o valor original.");
                                }
                            }
                            productService.updateProduct(productToUpdate);
                            System.out.println("Produto atualizado com sucesso!");
                        } else {
                            System.out.println("Produto não encontrado.");
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número ou um valor decimal válido.");
                scanner.nextLine();
                option = -1;
            } catch (Exception e) {
                System.err.println("Erro ao gerenciar produto: " + e.getMessage());
                option = -1;
            }
        } while (option != 0);
    }

    private static void handleOrderMenu(Scanner scanner, OrderService orderService, CustomerService customerService, ProductService productService) {
        int option;
        do {
            System.out.println("\n--- Gerenciar Pedidos ---");
            System.out.println("1. Criar Novo Pedido");
            System.out.println("2. Adicionar Item ao Pedido");
            System.out.println("3. Remover Item do Pedido");
            System.out.println("4. Alterar Quantidade de Item no Pedido");
            System.out.println("5. Finalizar Pedido");
            System.out.println("6. Processar Pagamento do Pedido");
            System.out.println("7. Registrar Entrega do Pedido");
            System.out.println("8. Buscar Pedido por ID");
            System.out.println("9. Listar Todos os Pedidos");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        System.out.print("ID do Cliente para o novo pedido: ");
                        String customerId = scanner.nextLine();
                        Optional<Customer> customerForOrder = customerService.findCustomerById(customerId);
                        if (customerForOrder.isPresent()) {
                            Order newOrder = orderService.createOrder(customerId);
                            System.out.println("Pedido criado com sucesso! ID: " + newOrder.getId());
                        } else {
                            System.out.println("Cliente não encontrado. Não foi possível criar o pedido.");
                        }
                        break;
                    case 2:
                        System.out.print("ID do Pedido: ");
                        String orderIdAddItem = scanner.nextLine();
                        System.out.print("ID do Produto: ");
                        String productIdAddItem = scanner.nextLine();
                        System.out.print("Quantidade: ");
                        int quantityAddItem = scanner.nextInt();
                        System.out.print("Valor de Venda (ex: 15.50): ");
                        BigDecimal saleValueAddItem = scanner.nextBigDecimal();
                        scanner.nextLine();
                        orderService.addItemToOrder(orderIdAddItem, productIdAddItem, quantityAddItem, saleValueAddItem);
                        System.out.println("Item adicionado ao pedido.");
                        break;
                    case 3:
                        System.out.print("ID do Pedido: ");
                        String orderIdRemoveItem = scanner.nextLine();
                        System.out.print("ID do Produto a remover: ");
                        String productIdRemoveItem = scanner.nextLine();
                        orderService.removeItemFromOrder(orderIdRemoveItem, productIdRemoveItem);
                        System.out.println("Item removido do pedido.");
                        break;
                    case 4:
                        System.out.print("ID do Pedido: ");
                        String orderIdUpdateItem = scanner.nextLine();
                        System.out.print("ID do Produto a alterar: ");
                        String productIdUpdateItem = scanner.nextLine();
                        System.out.print("Nova Quantidade: ");
                        int newQuantity = scanner.nextInt();
                        scanner.nextLine();
                        orderService.updateItemQuantity(orderIdUpdateItem, productIdUpdateItem, newQuantity);
                        System.out.println("Quantidade do item atualizada.");
                        break;
                    case 5:
                        System.out.print("ID do Pedido a finalizar: ");
                        String orderIdFinalize = scanner.nextLine();
                        orderService.finalizeOrder(orderIdFinalize);
                        System.out.println("Pedido finalizado e cliente notificado!");
                        break;
                    case 6:
                        System.out.print("ID do Pedido para processar pagamento: ");
                        String orderIdProcessPayment = scanner.nextLine();
                        orderService.processPayment(orderIdProcessPayment);
                        System.out.println("Pagamento processado e cliente notificado!");
                        break;
                    case 7:
                        System.out.print("ID do Pedido para registrar entrega: ");
                        String orderIdRegisterDelivery = scanner.nextLine();
                        orderService.registerDelivery(orderIdRegisterDelivery);
                        System.out.println("Entrega registrada e cliente notificado!");
                        break;
                    case 8:
                        System.out.print("ID do Pedido: ");
                        String idOrder = scanner.nextLine();
                        Optional<Order> foundOrder = orderService.findOrderById(idOrder);
                        foundOrder.ifPresentOrElse(
                                System.out::println,
                                () -> System.out.println("Pedido não encontrado.")
                        );
                        break;
                    case 9:
                        List<Order> orders = orderService.listAllOrders();
                        if (orders.isEmpty()) {
                            System.out.println("Nenhum pedido cadastrado.");
                        } else {
                            orders.forEach(System.out::println);
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número ou um valor decimal válido.");
                scanner.nextLine();
                option = -1;
            } catch (Exception e) {
                System.err.println("Erro ao gerenciar pedido: " + e.getMessage());
                option = -1;
            }
        } while (option != 0);
    }
}
