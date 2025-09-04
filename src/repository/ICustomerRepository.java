package repository;

import model.Customer;
import java.util.List;
import java.util.Optional;

public interface ICustomerRepository {
    void save(Customer customer);
    Optional<Customer> findById(String id);
    List<Customer> findAll();
    void update(Customer customer);
}



