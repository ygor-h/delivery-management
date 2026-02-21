package com.ygorhenrique.delivery_management.repository;

import com.ygorhenrique.delivery_management.domain.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
