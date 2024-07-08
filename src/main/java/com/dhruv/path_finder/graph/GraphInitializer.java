package com.dhruv.path_finder.graph;

import com.dhruv.path_finder.constants.ApplicationConstants;
import com.dhruv.path_finder.models.data.Customer;
import com.dhruv.path_finder.models.data.Driver;
import com.dhruv.path_finder.models.data.Restaurant;
import com.dhruv.path_finder.util.HaversineFormula;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Component for initializing the route optimization graph.
 */
@Component
public class GraphInitializer {

    /**
     * Constructs the route optimization graph with the given driver, restaurants, and customers.
     *
     * @param graph the route optimization graph
     * @param driver the driver
     * @param restaurantList the list of restaurants
     * @param customerList the list of customers
     */
    public void constructGraph(RouteOptimizationGraph graph, Driver driver, List<Restaurant> restaurantList, List<Customer> customerList) {
        initializePrepTimes(graph, restaurantList);
        addEdgeDriverToRestaurant(graph, driver, restaurantList);
        addEdgeResToCustomer(graph, restaurantList, customerList);
        addEdgeBetweenRes(graph, restaurantList);
        addEdgeBetweenCustomer(graph, customerList);
    }

    /**
     * Initializes the preparation times for the restaurants.
     *
     * @param graph the route optimization graph
     * @param restaurantList the list of restaurants
     */
    private void initializePrepTimes(RouteOptimizationGraph graph, List<Restaurant> restaurantList) {
        for (Restaurant restaurant : restaurantList) {
            graph.getRestaurantPrepTimes().put(restaurant.getRestaurantId(), restaurant.getPrepTime());
        }
    }

    /**
     * Adds edges between the driver and restaurants based on time.
     *
     * @param graph the route optimization graph
     * @param driver the driver
     * @param restaurantList the list of restaurants
     */
    private void addEdgeDriverToRestaurant(RouteOptimizationGraph graph, Driver driver, List<Restaurant> restaurantList) {
        for (Restaurant restaurant : restaurantList) {
            double distBetweenResAndDriver = HaversineFormula.distance(driver.getLocation(), restaurant.getLocation());
            double time = distBetweenResAndDriver / ApplicationConstants.AVERAGE_SPEED_KM_HR;
            System.out.println("Time --------" + time);
            graph.addEdge(driver.getDriverId(), restaurant.getRestaurantId(), time);
        }
    }

    /**
     * Adds edges between restaurants and customers based on time.
     *
     * @param graph the route optimization graph
     * @param restaurantList the list of restaurants
     * @param customerList the list of customers
     */
    private void addEdgeResToCustomer(RouteOptimizationGraph graph, List<Restaurant> restaurantList, List<Customer> customerList) {
        for (Restaurant restaurant : restaurantList) {
            for (Customer customer : customerList) {
                double distBetweenResAndCustomer = HaversineFormula.distance(restaurant.getLocation(), customer.getLocation());
                double time = distBetweenResAndCustomer / ApplicationConstants.AVERAGE_SPEED_KM_HR;
                System.out.println("Time --------" + time);
                graph.addEdge(restaurant.getRestaurantId(), customer.getCustomerId(), time);
                graph.addCustomerToRestaurant(customer.getCustomerId(), restaurant.getRestaurantId());
            }
        }
    }

    /**
     * Adds edges between all pairs of restaurants based on time.
     *
     * @param graph the route optimization graph
     * @param restaurantList the list of restaurants
     */
    private void addEdgeBetweenRes(RouteOptimizationGraph graph, List<Restaurant> restaurantList) {
        for (Restaurant restaurant1 : restaurantList) {
            for (Restaurant restaurant2 : restaurantList) {
                if (!restaurant1.getRestaurantId().equals(restaurant2.getRestaurantId())) {
                    double distanceBetweenRes = HaversineFormula.distance(restaurant1.getLocation(), restaurant2.getLocation());
                    double time = distanceBetweenRes / ApplicationConstants.AVERAGE_SPEED_KM_HR;
                    System.out.println("Time --------" + time);
                    graph.addEdge(restaurant1.getRestaurantId(), restaurant2.getRestaurantId(), time);
                }
            }
        }
    }

    /**
     * Adds edges between all pairs of customers based on time.
     *
     * @param graph the route optimization graph
     * @param customerList the list of customers
     */
    private void addEdgeBetweenCustomer(RouteOptimizationGraph graph, List<Customer> customerList) {
        for (Customer customer1 : customerList) {
            for (Customer customer2 : customerList) {
                if (!customer1.getCustomerId().equals(customer2.getCustomerId())) {
                    double distanceBetweenCustomer = HaversineFormula.distance(customer1.getLocation(), customer2.getLocation());
                    double time = distanceBetweenCustomer / ApplicationConstants.AVERAGE_SPEED_KM_HR;
                    System.out.println("Time --------" + time);
                    graph.addEdge(customer1.getCustomerId(), customer2.getCustomerId(), time);
                }
            }
        }
    }
}
