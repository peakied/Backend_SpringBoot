package com.peak.main.controller;

import com.peak.main.model.Customer;
import com.peak.main.repository.CustomerRepository;
import com.peak.security.model.RegisterRequest;
import com.peak.security.model.Response;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class customer {

    private final CustomerRepository customerRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/check")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String hello() {
        return "Hello USER";
    }

    @DeleteMapping("{customerId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteCustomer(@PathVariable("customerId") ObjectId id) {
        customerRepository.delete(customerRepository.findBy_id(id));
    }

    @PutMapping("/{cid}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response> update(@RequestBody RegisterRequest request, @PathVariable String cid) {
        return ResponseEntity.ok().body(new Response("ha"));
    }
}
