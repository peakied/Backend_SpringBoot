package com.peak.repository;

import com.peak.model.Customer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CustomerRepository extends MongoRepository<Customer, Integer> {

    Customer findBy_id(ObjectId _id);
}
