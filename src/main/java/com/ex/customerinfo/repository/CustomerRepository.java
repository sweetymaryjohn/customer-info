package com.ex.customerinfo.repository;

import com.ex.customerinfo.model.db.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCustomerRef(Long customerRef);
}
