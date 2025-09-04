package service;

import model.Product;
import util.IdGenerator;

public class ProductService {
    private final IProductRepository productRepository;
    private final IdGenerator idGenerator;

    public ProductService(IProductRepository productRepository, IdGenerator idGenerator) {}

    public void registerProduct(String name, String description, double value) {}

    public Optional<Product> findProductById(String id) {}

    public List<Product> listAllProducts() {}

    public void updateProduct(Product product) {}




}
