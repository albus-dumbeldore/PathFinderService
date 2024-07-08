package com.dhruv.path_finder.service;

import com.dhruv.path_finder.dto.request.RouteOptimizerRequestDTO;
import com.dhruv.path_finder.dto.response.RouteStepResponseDTO;
import com.dhruv.path_finder.graph.*;
import com.dhruv.path_finder.models.data.*;
import com.dhruv.path_finder.models.enums.EntityType;
import com.dhruv.path_finder.repository.repositoryInterface.RouteOptimizerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helper class for route optimization.
 */
public abstract class RouteOptimizationHelper {

    @Autowired
    protected OrderService orderService;
    @Autowired
    protected RestaurantService restaurantService;
    @Autowired
    protected CustomerService customerService;
    @Autowired
    protected DriverService driverService;
    @Autowired
    protected GraphInitializer graphInitializer;
    @Autowired
    protected GraphTraversal graphTraversal;
    @Autowired
    protected SlaCheckerAndRouteAdjuster slaCheckerAndRouteAdjuster;
    @Autowired
    protected RouteOptimizerRepository routeOptimizerRepository;

    /**
     * Builds the request object for route optimization.
     *
     * @param routeOptimizerRequestDTO the request DTO containing route optimization details
     * @return the route optimization result containing the driver ID and batch ID
     */
    protected RouteOptimizationResult buildRequest(RouteOptimizerRequestDTO routeOptimizerRequestDTO) {
        return RouteOptimizationResult.builder()
                .driverId(routeOptimizerRequestDTO.getDriverId())
                .batchId(routeOptimizerRequestDTO.getBatchId())
                .build();
    }

    /**
     * Fetches the details of orders, restaurants, and customers.
     *
     * @param orderIds       the list of order IDs
     * @param orderList      the list to populate with orders
     * @param restaurantList the list to populate with restaurants
     * @param customerList   the list to populate with customers
     */
    protected void fetchOrderDetails(List<String> orderIds, List<Order> orderList, List<Restaurant> restaurantList, List<Customer> customerList) {
        for (String orderId : orderIds) {
            Order order = orderService.getOrder(orderId);
            Restaurant restaurant = restaurantService.getRestaurant(order.getRestaurantId());
            Customer customer = customerService.getCustomer(order.getCustomerId());
            orderList.add(order);
            restaurantList.add(restaurant);
            customerList.add(customer);
        }
    }

    /**
     * Constructs the route optimization graph.
     *
     * @param driver         the driver
     * @param restaurantList the list of restaurants
     * @param customerList   the list of customers
     * @return the constructed route optimization graph
     */
    protected RouteOptimizationGraph constructGraph(Driver driver, List<Restaurant> restaurantList, List<Customer> customerList) {
        RouteOptimizationGraph routeOptimizationGraph = new RouteOptimizationGraph(restaurantList.size() + customerList.size() + 1);
        graphInitializer.constructGraph(routeOptimizationGraph, driver, restaurantList, customerList);
        return routeOptimizationGraph;
    }

    /**
     * Finds the best route for the driver.
     *
     * @param routeOptimizationGraph the route optimization graph
     * @param driver                 the driver
     * @return the list of UUIDs representing the best route
     */
    protected List<String> findBestRoute(RouteOptimizationGraph routeOptimizationGraph, Driver driver) {
        List<String> bestRoute = new ArrayList<>();
        double[] minTime = {Double.MAX_VALUE};
        graphTraversal.findBestRoute(routeOptimizationGraph, driver.getDriverId(), minTime, bestRoute);
        return bestRoute;
    }

    /**
     * Adjusts the route for SLA breaches.
     *
     * @param routeOptimizationGraph the route optimization graph
     * @param bestRoute              the best route
     * @param orderList              the list of orders
     * @return the adjusted route
     */
    protected List<String> adjustRouteForSlaBreaches(RouteOptimizationGraph routeOptimizationGraph, List<String> bestRoute, List<Order> orderList) {
        List<String> finalRoute = new ArrayList<>(Collections.singletonList(bestRoute.get(0)));
        List<String> breachedOrderIds = new ArrayList<>();
        slaCheckerAndRouteAdjuster.adjustRoute(bestRoute, finalRoute, breachedOrderIds, routeOptimizationGraph, orderList);
        return finalRoute;
    }

    /**
     * Builds the response object for route optimization.
     *
     * @param route         the route
     * @param driver        the driver
     * @param restaurantList the list of restaurants
     * @param customerList  the list of customers
     * @param totalTime     the total journey time
     * @return the route optimization result containing the driver ID, optimal route entities, and total time
     */
    protected RouteOptimizationResult buildResponse(List<String> route,
                                                    Driver driver,
                                                    List<Restaurant> restaurantList,
                                                    List<Customer> customerList,
                                                    double totalTime) {
        List<RouteStepResponseDTO> routeSteps = new ArrayList<>();

        for (String uuid : route) {
            if (uuid.equals(driver.getDriverId())) {
                routeSteps.add(new RouteStepResponseDTO(EntityType.DRIVER, uuid));
            } else if (restaurantList.stream().anyMatch(r -> r.getRestaurantId().equals(uuid))) {
                routeSteps.add(new RouteStepResponseDTO(EntityType.RESTAURANT, uuid));
            } else if (customerList.stream().anyMatch(c -> c.getCustomerId().equals(uuid))) {
                routeSteps.add(new RouteStepResponseDTO(EntityType.CUSTOMER, uuid));
            }
        }
        return RouteOptimizationResult.builder()
                .driverId(driver.getDriverId())
                .optimalRouteEntities(routeSteps)
                .time(totalTime)
                .build();
    }

    /**
     * Saves the path of the route optimization result.
     *
     * @param request  the request containing the batch ID
     * @param response the response containing the optimal route entities
     */
    protected void savePath(RouteOptimizationResult request,
                            RouteOptimizationResult response) {
        routeOptimizerRepository.savePath(request.getBatchId(),
                response.getOptimalRouteEntities());
    }

    /**
     * Calculates the total travel time for the final route.
     *
     * @param finalRoute the final route
     * @param graph      the route optimization graph
     * @return the total travel time
     */
    public double calculateTotalTime(List<String> finalRoute,
                                     RouteOptimizationGraph graph) {
        double totalTime = 0;
        for (int i = 0; i < finalRoute.size() - 1; i++) {
            String currentNode = finalRoute.get(i);
            String nextNode = finalRoute.get(i + 1);
            double travelTime = graph.getAdjacencyMatrix().get(currentNode).get(nextNode);
            double prepTime = graph.getRestaurantPrepTimes()
                    .getOrDefault(currentNode, 0.0) / 60;
            totalTime += travelTime + prepTime;
        }
        return totalTime;
    }
}
