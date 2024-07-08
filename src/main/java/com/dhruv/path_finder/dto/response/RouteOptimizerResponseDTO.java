package com.dhruv.path_finder.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class RouteOptimizerResponseDTO {
    private Double time;
    private List<RouteStepResponseDTO> routeEntities;
}
