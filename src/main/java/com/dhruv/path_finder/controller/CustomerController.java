package com.dhruv.path_finder.controller;

import com.dhruv.path_finder.dto.request.CustomerDTO;
import com.dhruv.path_finder.dto.response.CustomerResponseDTO;
import com.dhruv.path_finder.models.data.Customer;
import com.dhruv.path_finder.service.CustomerService;
import com.dhruv.path_finder.util.ResponseDTOConversion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing customers.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/pathfinder/v1")
@Validated
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Creates a new customer.
     *
     * @param customerDTO the customer DTO containing the customer details
     * @return a ResponseEntity containing the ID of the newly created customer
     */
    @Operation(summary = "API for creating customer")
    @ApiResponse(responseCode = "200", description = "Customer id")
    @PostMapping("/customer")
    public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        String id = customerService.createCustomer(customerDTO);
        return ResponseEntity.ok("Customer created with the id -> " + id);
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param id the ID of the customer
     * @return a ResponseEntity containing the customer details
     */
    @Operation(summary = "API for fetching customer")
    @ApiResponse(responseCode = "200", description = "Customer")
    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomer(@PathVariable String id) {
        Customer customer = customerService.getCustomer(id);
        CustomerResponseDTO customerResponseDTO = ResponseDTOConversion.convertToCustomerResponseDto(customer);
        return ResponseEntity.ok(customerResponseDTO);
    }

    /**
     * Retrieves all customers.
     *
     * @return a ResponseEntity containing the list of all customers
     */
    @Operation(summary = "API for fetching all customer")
    @ApiResponse(responseCode = "200", description = "All Customer")
    @GetMapping("/customer")
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomer() {
        List<Customer> customerList = customerService.getAllCustomer();
        List<CustomerResponseDTO> customerResponseDTOList = customerList.stream()
                .map(ResponseDTOConversion::convertToCustomerResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerResponseDTOList);
    }
}
