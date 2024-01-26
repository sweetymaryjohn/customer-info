package com.ex.customerinfo.service;
import com.ex.customerinfo.model.Customer;
import com.ex.customerinfo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(com.ex.customerinfo.model.db.Customer.builder()
                .customerRef(customer.getCustomerRef())
                .customerName(customer.getCustomerName())
                .addressLine1(customer.getAddressLine1())
                .addressLine2(customer.getAddressLine2())
                .town(customer.getTown())
                .county(customer.getCounty())
                .country(customer.getCountry())
                .postcode(customer.getPostcode())
                .build());
    }

    public com.ex.customerinfo.model.Customer getCustomerByRef(Long customerRef) {
        com.ex.customerinfo.model.db.Customer customer = customerRepository.findByCustomerRef(customerRef);

        if (customer == null) {
            return null;
        }

        return Customer.builder()
                .customerRef(customer.getCustomerRef())
                .customerName(customer.getCustomerName())
                .addressLine1(customer.getAddressLine1())
                .addressLine2(customer.getAddressLine2())
                .town(customer.getTown())
                .county(customer.getCounty())
                .country(customer.getCountry())
                .postcode(customer.getPostcode())
                .build();
    }
}
