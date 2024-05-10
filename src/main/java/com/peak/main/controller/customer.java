package com.peak.main.controller;

import com.peak.main.model.Customer;
import com.peak.main.repository.CustomerRepository;
import com.peak.security.model.RegisterRequest;
import com.peak.main.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class customer {

    private final CustomerRepository customerRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response> getCustomers() {
        return ResponseEntity.ok(new Response(customerRepository.findAll()));
    }

    @GetMapping("/me")
    public ResponseEntity<Response> getMe(Authentication authentication) {
        return ResponseEntity.ok(new Response(customerRepository.findByEmail(authentication.getName())));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response> deleteCustomer(Authentication authentication) {
        Optional<Customer> customer = customerRepository.findByEmail(authentication.getName());
        if (customer.isEmpty()) return ResponseEntity.notFound().build();
        customerRepository.delete(customer.get());
        return ResponseEntity.ok(new Response("[]"));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response> update(@RequestBody RegisterRequest requestUpdate) {
        Optional<Customer> customer = customerRepository.findByEmail(requestUpdate.getEmail());
        if (customer.isEmpty()) return ResponseEntity.notFound().build();
        customer.get().setName(requestUpdate.getName());
        customer.get().setPassword(requestUpdate.getPassword());
        customerRepository.save(customer.get());
        return ResponseEntity.ok().body(new Response(customer.get()));
    }
}
