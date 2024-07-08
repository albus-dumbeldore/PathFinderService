package com.dhruv.path_finder.repository.impl;

import com.dhruv.path_finder.exception.NotFoundException;
import com.dhruv.path_finder.models.data.Customer;
import com.dhruv.path_finder.repository.repositoryInterface.CustomerRepository;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

    private Map<String, Customer> customerMap;
    private Map<String, Boolean> customerExistsMap;
    private Map<String, List<String>> customerOrderHistoryMap;

    InMemoryCustomerRepository() {
        customerMap = new HashMap<>();
        customerExistsMap = new HashMap<>();
        customerOrderHistoryMap = new HashMap<>();
    }

    @Override
    public String createCustomer(@Nonnull Customer customer) {
        String customerId = UUID.randomUUID().toString();
        customer.setCustomerId(customerId);
        customerExistsMap.put(customer.getEmail(), true);
        customerMap.put(customerId, customer);
        return customerId;
    }

    public void saveCustomerOrder(String customerId, String orderId) {
        customerOrderHistoryMap.computeIfAbsent(customerId, id -> new ArrayList<>()).add(orderId);
    }

    @Override
    public Customer getCustomer(@Nonnull String id) {
        if (isCustomerExists(id)) {
            return customerMap.get(id);
        }
        throw new NotFoundException("Customer not found with this id -> " + id);
    }

    @Override
    public List<Customer> getAllCustomer() {
        List<Customer> customerList = new ArrayList<>();
        for (Customer customer : customerList) {
            customerList.add(customer);
        }
        return customerList;
    }

    private boolean isCustomerExists(String id) {
        return customerMap.get(id) != null;
    }

}
