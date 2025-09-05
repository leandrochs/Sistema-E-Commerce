package repository;

import model.Product;
import util.FileUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductFileRepository implements IProductRepository {

    private final String filePath;
    private final FileUtils fileUtils;

    private static final String FIELD_DELIMITER = ";";
    private static final int ID_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int DESCRIPTION_INDEX = 2;
    private static final int VALUE_INDEX = 3;
    private static final int EXPECTED_FIELD_COUNT = 4;

    public ProductFileRepository(String filePath, FileUtils fileUtils) {
        this.filePath = filePath;
        this.fileUtils = fileUtils;
    }

    @Override
    public void save(Product product) {
        List<Product> products = loadProducts();
        boolean found = false;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(product.getId())) {
                products.set(i, product);
                found = true;
                break;
            }
        }
        if (!found) {
            products.add(product);
        }
        writeProducts(products);
        System.out.println("Produto salvo com sucesso: " + product.getName());
    }

    @Override
    public Optional<Product> findById(String id) {
        return loadProducts().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> findAll() {
        return loadProducts();
    }

    @Override
    public void update(Product product) {
        List<Product> products = loadProducts();
        boolean updated = false;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(product.getId())) {
                products.set(i, product);
                updated = true;
                break;
            }
        }
        if (updated) {
            writeProducts(products);
            System.out.println("Produto atualizado com sucesso: " + product.getName());
        } else {
            System.err.println("Erro: Produto com ID " + product.getId() + " não encontrado para atualização.");
        }
    }

    private List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        List<String> lines = fileUtils.readFromFile(filePath);
        for (String line : lines) {
            try {
                String[] parts = line.split(FIELD_DELIMITER);
                if (parts.length == EXPECTED_FIELD_COUNT) {
                    String id = parts[ID_INDEX];
                    String name = parts[NAME_INDEX];
                    String description = parts[DESCRIPTION_INDEX];
                    BigDecimal value = new BigDecimal(parts[VALUE_INDEX]);

                    products.add(new Product(id, name, description, value));
                } else {
                    System.err.println("Linha mal formatada no arquivo de produtos: " + line);
                }
            } catch (NumberFormatException e) {
                System.err.println("Erro de formato numérico ao ler valor do produto: " + line + ". Erro: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Erro inesperado ao ler linha do produto: " + line + ". Erro: " + e.getMessage());
            }
        }
        return products;
    }

    private void writeProducts(List<Product> products) {
        List<String> lines = products.stream()
                .map(product -> product.getId() + FIELD_DELIMITER +
                        product.getName() + FIELD_DELIMITER +
                        product.getDescription() + FIELD_DELIMITER +
                        product.getValue().toPlainString())
                .collect(Collectors.toList());
        fileUtils.writeAllToFile(filePath, lines);
    }
}
