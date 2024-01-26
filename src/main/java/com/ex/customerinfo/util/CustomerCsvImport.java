package com.ex.customerinfo.util;

import com.ex.customerinfo.model.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CustomerCsvImport implements CommandLineRunner {

    private final String ADD_CUSTOMER_URL = "http://localhost:8080/api/customers";

    private final WebClient webClient;
    private final ResourceLoader resourceLoader;

    @Autowired
    public CustomerCsvImport(WebClient.Builder webClientBuilder, ResourceLoader resourceLoader) {
        this.webClient = webClientBuilder.baseUrl(ADD_CUSTOMER_URL).build();
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(String... args) throws Exception {

        String csvFilePath = "classpath:customers.csv";
        readAndInsertCsvData(csvFilePath);
    }

    public void readAndInsertCsvData(String filePath) throws IOException {

        Resource resource = resourceLoader.getResource(filePath);
        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String> lines = reader.lines()
                    .skip(1)
                    .collect(Collectors.toList());
            lines.stream()
                    .map(line -> line.split(","))
                    .forEach(fields -> {
                        Customer customer = Customer.builder()
                                .customerRef(Long.valueOf(fields[0]))
                                .customerName(fields[1])
                                .addressLine1(fields[2])
                                .addressLine2(fields[3])
                                .town(fields[4])
                                .county(fields[5])
                                .country(fields[6])
                                .postcode(fields[7])
                                .build();

                        webClient.post()
                                .body(BodyInserters.fromValue(customer))
                                .retrieve()
                                .toBodilessEntity()
                                .block();
                    });

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error while reading CSV file: " + e.getMessage());
        }
    }
}