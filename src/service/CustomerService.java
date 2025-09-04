package service;

import model.Customer;
import util.IdGenerator;

import java.util.List;
import java.util.Optional;

public class CustomerService {
    private final ICustomerRepository customerRepository;
    private final IdGenerator idGenerator;

    public CustomerService(ICustomerRepository customerRepository, IdGenerator idGenerator) {}

    public void registerCustomer(String name, String identificationDocument) {}

    public Optional<Customer> findCustomerById(String id) {}

    public List<Customer> listAllCustomers() {}

    public void updateCustomer(Customer customer) {}



}
