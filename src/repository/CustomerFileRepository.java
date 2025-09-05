package repository;

import model.Customer;
import util.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerFileRepository implements ICustomerRepository {

    private static final String FIELD_DELIMITER = ";";
    private static final int ID_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int IDENTIFICATION_DOCUMENT_INDEX = 2;
    private static final int EXPECTED_FIELD_COUNT = 3;

    private final String filePath;
    private final FileUtils fileUtils;

    public CustomerFileRepository(String filePath, FileUtils fileUtils) {
        this.filePath = filePath;
        this.fileUtils = fileUtils;
    }

    @Override
    public void save(Customer customer) {
        String customerData = String.join(FIELD_DELIMITER,
                customer.getId(),
                customer.getName(),
                customer.getIdentificationDocument());
        fileUtils.writeToFile(filePath, customerData, true);
    }

    @Override
    public Optional<Customer> findById(String id) {
        return loadCustomers().stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Customer> findAll() {
        return loadCustomers();
    }

    @Override
    public void update(Customer updatedCustomer) {
        List<Customer> customers = loadCustomers();
        boolean found = false;
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equals(updatedCustomer.getId())) {
                customers.set(i, updatedCustomer);
                found = true;
                break;
            }
        }
        if (found) {
            writeCustomers(customers);
        } else {
            System.err.println("Cliente com ID " + updatedCustomer.getId() + " não encontrado para atualização.");
        }
    }

    private List<Customer> loadCustomers() {
        List<String> lines = fileUtils.readFromFile(filePath);
        List<Customer> customers = new ArrayList<>();
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split(FIELD_DELIMITER);
            if (parts.length == EXPECTED_FIELD_COUNT) {
                try {
                    String id = parts[ID_INDEX].trim();
                    String name = parts[NAME_INDEX].trim();
                    String identificationDocument = parts[IDENTIFICATION_DOCUMENT_INDEX].trim();
                    customers.add(new Customer(id, name, identificationDocument));
                } catch (Exception e) {
                    System.err.println("Erro ao parsear linha do cliente: \"" + line + "\". Detalhe: " + e.getMessage());
                }
            } else {
                System.err.println("Formato de linha inválido no arquivo de clientes. Linha: \"" + line + "\". Esperado " + EXPECTED_FIELD_COUNT + " campos, encontrado " + parts.length + ".");
            }
        }
        return customers;
    }

    private void writeCustomers(List<Customer> customers) {
        List<String> lines = customers.stream()
                .map(customer -> String.join(FIELD_DELIMITER,
                        customer.getId(),
                        customer.getName(),
                        customer.getIdentificationDocument()))
                .collect(Collectors.toList());
        fileUtils.writeAllToFile(filePath, lines);
    }
}
