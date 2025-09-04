package service;

import model.Customer;
import model.Order;

public class NotificationService {

    public void sendPaymentPendingNotification(Customer customer, Order order) {
        System.out.println("--------------------------------------------------");
        System.out.println("E-mail para: " + customer.getName() + " (ID: " + customer.getId() + ")");
        System.out.println("Assunto: Seu pedido #" + order.getId() + " está aguardando pagamento.");
        System.out.println("Prezado(a) " + customer.getName() + ",");
        System.out.println("Informamos que o seu pedido #" + order.getId() + ", criado em " + order.getCreationDate() + ", está aguardando a confirmação do pagamento.");
        System.out.println("Agradecemos a sua compra!");
        System.out.println("--------------------------------------------------");
    }

    public void sendPaymentConfirmedNotification(Customer customer, Order order) {
        System.out.println("--------------------------------------------------");
        System.out.println("E-mail para: " + customer.getName() + " (ID: " + customer.getId() + ")");
        System.out.println("Assunto: Pagamento do seu pedido #" + order.getId() + " confirmado!");
        System.out.println("Prezado(a) " + customer.getName() + ",");
        System.out.println("Temos o prazer de informar que o pagamento do seu pedido #" + order.getId() + " foi confirmado com sucesso.");
        System.out.println("Em breve, seu pedido será preparado para entrega.");
        System.out.println("Agradecemos a sua compra!");
        System.out.println("--------------------------------------------------");
    }

    public void sendDeliveryNotification(Customer customer, Order order) {}


}
