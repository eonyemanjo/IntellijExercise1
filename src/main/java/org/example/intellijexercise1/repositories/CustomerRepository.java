package org.example.intellijexercise1.repositories;

import org.example.intellijexercise1.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Method to find a customer by email
    Customer findByEmail(String email);

    // Method to delete a customer by email
    void deleteByEmail(String email);
}
