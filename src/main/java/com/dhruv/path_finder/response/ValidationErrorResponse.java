package com.dhruv.path_finder.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Response class for validation errors.
 */
@Getter
@Setter
public class ValidationErrorResponse {
    private String message;
    private List<String> details;

    /**
     * Constructs a new ValidationErrorResponse with the specified message and details.
     *
     * @param message the error message
     * @param details the list of error details
     */
    public ValidationErrorResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }
}

