package repository;

import model.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductRepository {
    void save(Product product);
    Optional<Product> findById(UUID id);
    List<Product> findAll();
    void update(Product product);
}






