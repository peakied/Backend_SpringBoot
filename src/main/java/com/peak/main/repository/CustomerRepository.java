package com.peak.main.repository;

import com.peak.main.model.Customer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);

    Customer findBy_id(ObjectId id);
}
