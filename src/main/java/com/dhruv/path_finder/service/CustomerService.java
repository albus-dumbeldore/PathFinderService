package com.dhruv.path_finder.service;

import com.dhruv.path_finder.dto.request.CustomerDTO;
import com.dhruv.path_finder.models.data.Customer;
import com.dhruv.path_finder.repository.repositoryInterface.CustomerRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service class for managing customer operations.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Creates a new customer using the provided customer DTO.
     *
     * @param customerDTO the customer DTO containing the customer details
     * @return the ID of the newly created customer
     */
    public String createCustomer(@Nonnull CustomerDTO customerDTO) {
        Customer customer = Customer.builder()
                .email(customerDTO.getEmail())
                .name(customerDTO.getName())
                .location(customerDTO.getLocation())
                .createdAt(new Date())
                .build();

        return customerRepository.createCustomer(customer);
    }

    /**
     * Saves the association between a customer and an order.
     *
     * @param customerId the ID of the customer
     * @param orderId    the ID of the order
     */
    public void saveCustomerOrderDetails(String customerId, String orderId) {
        customerRepository.saveCustomerOrder(customerId, orderId);
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param id the ID of the customer
     * @return the Customer object
     */
    public Customer getCustomer(@Nonnull String id) {
        return customerRepository.getCustomer(id);
    }

    /**
     * Retrieves all customers.
     *
     * @return the list of all customers
     */
    public List<Customer> getAllCustomer() {
        return customerRepository.getAllCustomer();
    }

}
