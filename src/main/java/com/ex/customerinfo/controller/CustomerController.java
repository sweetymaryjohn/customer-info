package com.ex.customerinfo.controller;

import com.ex.customerinfo.model.Customer;
import com.ex.customerinfo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
        customerService.saveCustomer(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{customerRef}")
    public ResponseEntity<Customer> getCustomerByRef(@PathVariable Long customerRef) {
        Customer customer = customerService.getCustomerByRef(customerRef);

        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
