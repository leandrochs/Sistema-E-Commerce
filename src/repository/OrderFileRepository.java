package repository;

import model.Order;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderFileRepository implements IOrderRepository {
    private final String filePath = "orders.txt";

    @Override
    public void save(Order order) {
        List<Order> orders = findAll();
        orders.add(order);
        writeAll(orders);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return findAll().stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                try {
                    orders.add((Order) ois.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo não existe, retorna lista vazia
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public void update(Order order) {
        List<Order> orders = findAll();
        List<Order> updatedOrders = orders.stream()
                .map(o -> o.getId().equals(order.getId()) ? order : o)
                .collect(Collectors.toList());
        writeAll(updatedOrders);
    }

    private void writeAll(List<Order> orders) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Order o : orders) {
                oos.writeObject(o);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}