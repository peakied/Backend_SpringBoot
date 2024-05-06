package com.peak.controller;

import com.peak.model.Customer;
import com.peak.repository.CustomerRepository;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/customers")
public class customer {

    public customer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    private final CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }


    record NewCustomerRequest(
            String name,
            String email,
            String password
    ) {}

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer(request.name, request.email, request.password);
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") ObjectId id) {
        customerRepository.delete(customerRepository.findBy_id(id));
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") ObjectId id, @RequestBody NewCustomerRequest request) {
        Customer customer = customerRepository.findBy_id(id);
        customer.setName(request.name());
        customer.setEmail(request.email());
        customerRepository.save(customer);
    }
}
