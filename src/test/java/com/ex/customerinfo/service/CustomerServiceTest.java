package com.ex.customerinfo.service;

import com.ex.customerinfo.model.Customer;
import com.ex.customerinfo.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        Customer customer = new Customer();
        customer.setCustomerRef(1L);
        customer.setCustomerName("Test Customer");
        customer.setAddressLine1("Address Line 1");
        customer.setAddressLine2("Address Line 2");
        customer.setTown("Town");
        customer.setCounty("County");
        customer.setCountry("Country");
        customer.setPostcode("12345");

        customerService.saveCustomer(customer);

        verify(customerRepository, times(1)).save(any());
    }

    @Test
    void testGetCustomerByRef() {
        com.ex.customerinfo.model.db.Customer dbCustomer = createDbCustomer();
        when(customerRepository.findByCustomerRef(1L)).thenReturn(dbCustomer);

        com.ex.customerinfo.model.Customer result = customerService.getCustomerByRef(1L);

        verify(customerRepository, times(1)).findByCustomerRef(1L);

        assertEquals(dbCustomer.getCustomerRef(), result.getCustomerRef());
        assertEquals(dbCustomer.getCustomerName(), result.getCustomerName());
        assertEquals(dbCustomer.getAddressLine1(), result.getAddressLine1());
        assertEquals(dbCustomer.getAddressLine2(), result.getAddressLine2());
        assertEquals(dbCustomer.getTown(), result.getTown());
        assertEquals(dbCustomer.getCounty(), result.getCounty());
        assertEquals(dbCustomer.getCountry(), result.getCountry());
        assertEquals(dbCustomer.getPostcode(), result.getPostcode());
    }

    @Test
    void getCustomerByRef_nonExistingCustomer() {
        when(customerRepository.findByCustomerRef(1L)).thenReturn(null);

        com.ex.customerinfo.model.Customer result = customerService.getCustomerByRef(1L);

        verify(customerRepository, times(1)).findByCustomerRef(1L);

        assertEquals(null, result);
    }

    private com.ex.customerinfo.model.db.Customer createDbCustomer() {
        return com.ex.customerinfo.model.db.Customer.builder()
                .customerRef(1L)
                .customerName("Test Customer")
                .addressLine1("Address Line 1")
                .addressLine2("Address Line 2")
                .town("Town")
                .county("County")
                .country("Country")
                .postcode("12345")
                .build();
    }
}
