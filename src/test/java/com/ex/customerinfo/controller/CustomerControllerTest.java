package com.ex.customerinfo.controller;

import com.ex.customerinfo.model.Customer;
import com.ex.customerinfo.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    void testAddCustomer() throws Exception {
        Customer customerToAdd = createSampleCustomer();

        doNothing().when(customerService).saveCustomer(customerToAdd);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerToAdd)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testGetCustomerByRef() throws Exception {
        Long customerRef = 1L;
        Customer foundCustomer = createSampleCustomer();

        when(customerService.getCustomerByRef(customerRef)).thenReturn(foundCustomer);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/{customerRef}", customerRef)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerRef").value(foundCustomer.getCustomerRef()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value(foundCustomer.getCustomerName()));
    }

    @Test
    void testAddCustomerBadRequest() throws Exception {
        Customer customerToAdd = createInvalidCustomer();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerToAdd)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testGetCustomerByRefNotFound() throws Exception {
        Long invalidCustomerRef = -1L;

        when(customerService.getCustomerByRef(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/{customerRef}", invalidCustomerRef)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private Customer createInvalidCustomer() {
        return Customer.builder()
                .customerRef(1L)
                .addressLine1("123 Main St")
                .town("Cityville")
                .country("Countryland")
                .postcode("SW1A 2AA")
                .build();
    }

    private Customer createSampleCustomer() {
        return Customer.builder()
                .customerRef(1L)
                .customerName("John Doe")
                .addressLine1("123 Main St")
                .town("London")
                .country("London")
                .postcode("SW1A 2AA")
                .build();
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
