package com.dhruv.path_finder.graph;

import java.util.HashMap;
import java.util.Map;


/**
 * Class representing a graph for route optimization.
 */
public class RouteOptimizationGraph {
    private final int totalNodes;
    private final Map<String, Map<String, Double>> adjacencyMatrix;
    private final Map<String, Double> restaurantPrepTimes;
    private final Map<String, String> customerToRestaurant;

    public RouteOptimizationGraph(int totalNodes) {
        this.adjacencyMatrix = new HashMap<>();
        this.customerToRestaurant = new HashMap<>();
        this.restaurantPrepTimes = new HashMap<>();
        this.totalNodes = totalNodes;
    }

    public int getTotalNodes() {
        return totalNodes;
    }

    public Map<String, Map<String, Double>> getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public Map<String, Double> getRestaurantPrepTimes() {
        return restaurantPrepTimes;
    }

    public Map<String, String> getCustomerToRestaurant() {
        return customerToRestaurant;
    }

    /**
     * Adds an edge between two nodes with the specified distance.
     *
     * @param from the starting node
     * @param to the ending node
     * @param distance the distance between the nodes
     */
    public void addEdge(String from, String to, double distance) {
        adjacencyMatrix.computeIfAbsent(from, k -> new HashMap<>()).put(to, distance);
        adjacencyMatrix.computeIfAbsent(to, k -> new HashMap<>()).put(from, distance);
    }

    /**
     * Adds a mapping of a customer to a restaurant.
     *
     * @param customer the customer ID
     * @param restaurant the restaurant ID
     */
    public void addCustomerToRestaurant(String customer, String restaurant) {
        customerToRestaurant.put(customer, restaurant);
    }

}
