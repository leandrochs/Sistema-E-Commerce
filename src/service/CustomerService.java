package service;

import model.Customer;
import repository.ICustomerRepository;
import util.IdGenerator;

import java.util.List;
import java.util.Optional;

public class CustomerService {
    private final ICustomerRepository customerRepository;
    private final IdGenerator idGenerator;

    public CustomerService(ICustomerRepository customerRepository, IdGenerator idGenerator) {
        this.customerRepository = customerRepository;
        this.idGenerator = idGenerator;
    }

    public void registerCustomer(String name, String identificationDocument) {
        if (identificationDocument == null || identificationDocument.trim().isEmpty()) {
            throw new IllegalArgumentException("O documento de identificação é obrigatório para o cadastro do cliente.");
        }

        String id = idGenerator.generateId();
        Customer customer = new Customer(id, name, identificationDocument);
        customerRepository.save(customer);
    }

    public Optional<Customer> findCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public List<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }

    public void updateCustomer(Customer customer) {
        customerRepository.update(customer);
    }



}
