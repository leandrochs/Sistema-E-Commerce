package repository;

import model.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICustomerRepository {
    void save(Customer customer);
    Optional<Customer> findById(UUID id);
    List<Customer> findAll();
    void update(Customer customer);
}


