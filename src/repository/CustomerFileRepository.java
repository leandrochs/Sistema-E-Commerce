package repository;

import model.Customer;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerFileRepository implements ICustomerRepository {
    private final String fileName = "customers.txt";

    @Override
    public void save(Customer customer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(customer.getId() + ";" + customer.getName() + ";" + customer.getIdentificationDocument());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                UUID customerId = UUID.fromString(data[0]);
                if (customerId.equals(id)) {
                    return Optional.of(new Customer(customerId, data[1], data[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                customers.add(new Customer(UUID.fromString(data[0]), data[1], data[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void update(Customer customer) {
        List<Customer> customers = findAll();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            for (Customer c : customers) {
                if (c.getId().equals(customer.getId())) {
                    writer.write(customer.getId() + ";" + customer.getName() + ";" + customer.getIdentificationDocument());
                } else {
                    writer.write(c.getId() + ";" + c.getName() + ";" + c.getIdentificationDocument());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


