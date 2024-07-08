package com.dhruv.path_finder.graph;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Component for traversing the route optimization graph to find the best route.
 */
@Component
public class GraphTraversal {

    /**
     * Finds the best route starting from a given location.
     *
     * @param graph the route optimization graph
     * @param startLocation the starting location
     * @param minTime an array to store the minimum travel time
     * @param bestRoute the list to store the best route
     */
    public void findBestRoute(RouteOptimizationGraph graph, String startLocation, double[] minTime, List<String> bestRoute) {
        List<String> currentRoute = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        currentRoute.add(startLocation);
        visited.add(startLocation);
        backtrack(graph, startLocation, visited, currentRoute, 0, minTime, bestRoute);
    }

    /**
     * Backtracks through the graph to find the optimal route.
     *
     * @param graph the route optimization graph
     * @param current the current node
     * @param visited the set of visited nodes
     * @param currentRoute the current route being explored
     * @param currentJourneyTime the current journey time
     * @param minTime an array to store the minimum travel time
     * @param bestRoute the list to store the best route
     */
    private void backtrack(RouteOptimizationGraph graph, String current, Set<String> visited, List<String> currentRoute, double currentJourneyTime, double[] minTime, List<String> bestRoute) {
        if (visited.size() == graph.getTotalNodes()) {
            if (currentJourneyTime < minTime[0]) {
                minTime[0] = currentJourneyTime;
                bestRoute.clear();
                bestRoute.addAll(new ArrayList<>(currentRoute));
            }
            return;
        }

        for (Map.Entry<String, Double> entry : graph.getAdjacencyMatrix().get(current).entrySet()) {
            String neighbor = entry.getKey();
            double travelTime = entry.getValue();
            double prepTime = (graph.getRestaurantPrepTimes().getOrDefault(neighbor, 0.0)) / 60;

            double arrivalTime = currentJourneyTime + travelTime;
            double departureTime = Math.max(arrivalTime, prepTime);

            if (!visited.contains(neighbor) && canVisit(graph, neighbor, visited)) {
                visited.add(neighbor);
                currentRoute.add(neighbor);
                backtrack(graph, neighbor, visited, currentRoute, departureTime, minTime, bestRoute);
                currentRoute.remove(currentRoute.size() - 1);
                visited.remove(neighbor);
            }
        }
    }

    /**
     * Determines if a location can be visited based on the current state of the graph.
     *
     * @param graph the route optimization graph
     * @param location the location to check
     * @param visited the set of visited nodes
     * @return true if the location can be visited, false otherwise
     */
    private boolean canVisit(RouteOptimizationGraph graph, String location, Set<String> visited) {
        if (graph.getCustomerToRestaurant().containsKey(location)) {
            String requiredRestaurant = graph.getCustomerToRestaurant().get(location);
            return visited.contains(requiredRestaurant);
        }
        return true;
    }
}
