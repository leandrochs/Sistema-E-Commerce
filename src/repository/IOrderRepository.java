package repository;

import model.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository {
    void save(Order order);
    Optional<Order> findById(String id);
    List<Order> findAll();
    void update(Order order);
}


