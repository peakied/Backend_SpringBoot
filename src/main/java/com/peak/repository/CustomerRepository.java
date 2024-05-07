package com.peak.repository;

import com.peak.model.Customer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
    Customer findBy_id(ObjectId _id);
}
