package com.dhruv.path_finder.dto.response;

import com.dhruv.path_finder.models.enums.EntityType;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class RouteStepResponseDTO {
    // "DRIVER", "RESTAURANT", "CUSTOMER"
    private EntityType type;
    // UUID of the entity
    private String uuid;
}
