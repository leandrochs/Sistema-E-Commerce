#   Sistema de E-Commerce em Java

Este projeto é uma aplicação de **e-commerce** desenvolvida em **Java**, com foco em **boas práticas de programação orientada a objetos (POO)**, uso de **camadas de serviço e repositório**, além de **persistência em arquivos locais**.

---

##  Funcionalidades

✔️ Cadastro de clientes  
✔️ Cadastro de produtos  
✔️ Criação e gerenciamento de pedidos  
✔️ Associação de itens a pedidos  
✔️ Controle de status de pedido e pagamento  
✔️ Persistência em arquivos locais (sem necessidade de banco de dados)  
✔️ Notificações simples de operações  

---

## ️ Arquitetura

O sistema segue o padrão **camadas**, garantindo separação de responsabilidades:

- **application/** → Ponto de entrada da aplicação (`Main.java`)  
- **model/** → Entidades principais do domínio (`Customer`, `Product`, `Order`, etc.)  
- **repository/** → Interfaces e implementações de repositórios baseados em arquivos  
- **service/** → Contém a lógica de negócios (ex.: `OrderService`, `ProductService`)  
- **util/** → Classes utilitárias (`FileUtils`, `IdGenerator`)  

---

##  Estrutura de Pastas

```
src/
 ├── application/
 │    └── Main.java           # Classe principal
 ├── model/
 │    ├── Customer.java       # Cliente
 │    ├── Product.java        # Produto
 │    ├── Order.java          # Pedido
 │    ├── OrderItem.java      # Item do pedido
 │    ├── OrderStatus.java    # Enum status do pedido
 │    └── PaymentStatus.java  # Enum status do pagamento
 ├── repository/
 │    ├── ICustomerRepository.java
 │    ├── IProductRepository.java
 │    ├── IOrderRepository.java
 │    ├── CustomerFileRepository.java
 │    ├── ProductFileRepository.java
 │    └── OrderFileRepository.java
 ├── service/
 │    ├── CustomerService.java
 │    ├── ProductService.java
 │    ├── OrderService.java
 │    └── NotificationService.java
 └── util/
      ├── FileUtils.java
      └── IdGenerator.java
```

---

##  Principais Entidades

- **Customer** → Representa o cliente (nome, documento, id único).  
- **Product** → Representa um produto disponível para venda.  
- **Order** → Representa um pedido realizado pelo cliente.  
- **OrderItem** → Item vinculado ao pedido (produto + quantidade).  
- **OrderStatus** → Enum com os estados possíveis do pedido (ex.: `PENDING`, `CONFIRMED`).  
- **PaymentStatus** → Enum para controle do pagamento (ex.: `PAID`, `PENDING`).  

---

##  Como executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/ecommerce-java.git
   cd ecommerce-java
   ```

2. Compile o código:
   ```bash
   javac -d bin src/**/*.java
   ```

3. Execute a aplicação:
   ```bash
   java -cp bin application.Main
   ```

---

##  Exemplo de Uso

Dentro do `Main.java`, você pode criar instâncias de clientes, produtos e pedidos.  
Exemplo simplificado:

```java
CustomerService customerService = new CustomerService();
ProductService productService = new ProductService();
OrderService orderService = new OrderService(customerService, productService);

// Criar cliente
Customer customer = new Customer("João Silva", "12345678900");
customerService.registerCustomer(customer);

// Criar produto
Product product = new Product("Notebook", 3500.0);
productService.registerProduct(product);

// Criar pedido
Order order = orderService.createOrder(customer.getId());
orderService.addProductToOrder(order.getId(), product.getId(), 2);

// Confirmar pedido
orderService.confirmOrder(order.getId());
```

---

##  Próximos Passos

🔹 Criar testes unitários com **JUnit**  
🔹 Implementar persistência em banco de dados (MySQL ou PostgreSQL)  
🔹 Adicionar API REST usando **Spring Boot**  
🔹 Criar interface gráfica (JavaFX ou Swing)  

---

##  Tecnologias Utilizadas

- Java 17+  
- POO (Programação Orientada a Objetos)  
- Persistência em arquivos (`FileUtils`)  

---

##  Autor

- Desenvolvido por **Leandro Chagas/Leonardo Rodrigues**
