package repository;

import model.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductFileRepository implements IProductRepository {
    private final String filePath = "products.txt";

    @Override
    public void save(Product product) {
        List<Product> products = findAll();
        products.add(product);
        writeAll(products);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return findAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                try {
                    products.add((Product) ois.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void update(Product product) {
        List<Product> products = findAll();
        List<Product> updatedProducts = products.stream()
                .map(p -> p.getId().equals(product.getId()) ? product : p)
                .collect(Collectors.toList());
        writeAll(updatedProducts);
    }

    private void writeAll(List<Product> products) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Product p : products) {
                oos.writeObject(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
