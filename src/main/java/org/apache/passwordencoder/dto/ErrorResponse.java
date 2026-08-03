package org.apache.passwordencoder.dto;

/**
 * Represents a minimal API error response.
 * <p>
 * Contains only the error message.
 * </p>
 *
 * @param message error message explaining the failure
 */
public record ErrorResponse(String message) {
}
