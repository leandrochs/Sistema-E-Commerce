package repository;

import model.Customer;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomerFileRepository implements ICustomerRepository {
    private final String fileName = "customers.txt";

    @Override
    public void save(Customer customer) {
        List<Customer> customers = findAll();
        customers.add(customer);
        writeAll(customers);
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return findAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            while (true) {
                try {
                    customers.add((Customer) ois.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void update(Customer customer) {
        List<Customer> customers = findAll();
        List<Customer> updatedCustomers = customers.stream()
                .map(c -> c.getId().equals(customer.getId()) ? customer : c)
                .collect(Collectors.toList());
        writeAll(updatedCustomers);
    }

    private void writeAll(List<Customer> customers) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Customer c : customers) {
                oos.writeObject(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






