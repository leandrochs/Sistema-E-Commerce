package service;

import model.Product;
import util.IdGenerator;

import java.util.List;
import java.util.Optional;

public class ProductService {
    private final IProductRepository productRepository;
    private final IdGenerator idGenerator;

    public ProductService(IProductRepository productRepository, IdGenerator idGenerator, IProductRepository productRepository1, IdGenerator idGenerator1) {
        this.productRepository = productRepository1;
        this.idGenerator = idGenerator1;
    }

    public void registerProduct(String name, String description, double value) {}

    public Optional<Product> findProductById(String id) {}

    public List<Product> listAllProducts() {}

    public void updateProduct(Product product) {}




}
