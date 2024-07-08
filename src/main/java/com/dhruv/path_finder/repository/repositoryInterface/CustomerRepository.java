package com.dhruv.path_finder.repository.repositoryInterface;

import com.dhruv.path_finder.models.data.Customer;

import java.util.List;

/**
 * Repository interface for customer operations.
 */
public interface CustomerRepository {

    /**
     * Creates a new customer.
     *
     * @param customer the customer to create
     * @return the ID of the newly created customer
     */
    String createCustomer(Customer customer);

    /**
     * Retrieves a customer by their ID.
     *
     * @param id the ID of the customer
     * @return the Customer object
     */
    Customer getCustomer(String id);

    /**
     * Retrieves all customers.
     *
     * @return the list of all customers
     */
    List<Customer> getAllCustomer();

    /**
     * Saves the association between a customer and an order.
     *
     * @param customerId the ID of the customer
     * @param orderId the ID of the order
     */
    void saveCustomerOrder(String customerId, String orderId);
}
