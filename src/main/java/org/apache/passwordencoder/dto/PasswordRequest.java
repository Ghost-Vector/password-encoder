package org.apache.passwordencoder.dto;

/**
 * Data Transfer Object representing the incoming password encoding request.
 * <p>
 * Used by the REST API to carry the plaintext password only.
 * </p>
 *
 * @param password the plaintext password to be encoded
 */
public record PasswordRequest(String password) {
}
