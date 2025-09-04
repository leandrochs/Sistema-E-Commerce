package repository;

import model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IOrderRepository {
    void save(Order order);
    Optional<Order> findById(UUID id);
    List<Order> findAll();
    void update(Order order);
}

