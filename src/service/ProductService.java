package service;

import model.Product;
import util.IdGenerator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final IProductRepository productRepository;
    private final IdGenerator idGenerator;

    public ProductService(IProductRepository productRepository, IdGenerator idGenerator) {
        this.productRepository = productRepository;
        this.idGenerator = idGenerator;
    }

    public void registerProduct(String name, String description, BigDecimal value) {
        String id = idGenerator.generateId();
        Product product = new Product(id, name, description, value);
        productRepository.save(product);
        System.out.println("--------------------------------------------------");
        System.out.println("Produto '" + product.getName() + "' (ID: " + product.getId() + ") cadastrado com sucesso.");
        System.out.println("--------------------------------------------------");
    }

    public Optional<Product> findProductById(String id) {
        return productRepository.findById(id);
    }

    public List<Product> listAllProducts() {}

    public void updateProduct(Product product) {}




}
