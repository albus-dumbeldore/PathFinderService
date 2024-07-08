package com.dhruv.path_finder.service;

import com.dhruv.path_finder.models.data.*;
import com.dhruv.path_finder.dto.request.RouteOptimizerRequestDTO;
import com.dhruv.path_finder.graph.RouteOptimizationGraph;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for optimizing delivery routes.
 */
@Service
public class RouteOptimizerService extends RouteOptimizationHelper {

    public RouteOptimizationResult findPath(RouteOptimizerRequestDTO routeOptimizerRequestDTO) {
        RouteOptimizationResult request = buildRequest(routeOptimizerRequestDTO);

        List<Order> orderList = new ArrayList<>();
        List<String> orderIds = orderService.getOrderIdsByBatchId(request.getBatchId());
        List<Restaurant> restaurantList = new ArrayList<>();
        List<Customer> customerList = new ArrayList<>();
        Driver driver = driverService.getDriver(request.getDriverId());

        fetchOrderDetails(orderIds, orderList, restaurantList, customerList);

        RouteOptimizationGraph routeOptimizationGraph = constructGraph(driver, restaurantList, customerList);

        List<String> bestRoute = findBestRoute(routeOptimizationGraph, driver);

        List<String> finalRoute = adjustRouteForSlaBreaches(routeOptimizationGraph, bestRoute, orderList);

        double adjustedTotalTime = calculateTotalTime(finalRoute, routeOptimizationGraph);

        RouteOptimizationResult response = buildResponse(finalRoute, driver, restaurantList, customerList, adjustedTotalTime);

        savePath(request, response);

        return response;
    }


}
