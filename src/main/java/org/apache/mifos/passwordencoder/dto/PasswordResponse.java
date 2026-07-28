package org.apache.mifos.passwordencoder.dto;

/**
 * Data Transfer Object representing the encoded password returned by the API.
 * <p>
 * Carries the generated legacy password hash string.
 * </p>
 *
 * @param encodedPassword the generated legacy password hash
 */
public record PasswordResponse(String encodedPassword) {
}
