package repository;

import model.Product;
import utils.FileUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductFileRepository implements IProductRepository {
    private final String filePath = "products.txt";
    private final FileUtils fileUtils;

    public ProductFileRepository(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }

    @Override
    public void save(Product product) {
        fileUtils.appendLine(filePath, product.toString());
    }

    @Override
    public Optional<Product> findById(String id) {
        return findAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> findAll() {
        return fileUtils.readAllLines(filePath).stream()
                .map(Product::fromString)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Product product) {
        List<Product> products = findAll();
        List<Product> updatedProducts = products.stream()
                .map(p -> p.getId().equals(product.getId()) ? product : p)
                .collect(Collectors.toList());

        List<String> lines = updatedProducts.stream()
                .map(Product::toString)
                .collect(Collectors.toList());
        fileUtils.writeAllLines(filePath, lines);
    }
}