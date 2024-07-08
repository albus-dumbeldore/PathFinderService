package com.dhruv.path_finder.response;

import com.dhruv.path_finder.models.data.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Response class for route optimization results.
 */
@Getter
@Setter
@AllArgsConstructor
public class RouteOptimizerResponse {
    private Double time;
    private List<String> optimalRoute;
    private List<Customer> customerSlaBreachList;
}
