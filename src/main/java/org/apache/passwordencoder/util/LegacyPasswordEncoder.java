package org.apache.passwordencoder.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * Utility for generating legacy password hashes.
 * <p>
 * Reproduces the legacy password encoding format for manual tenant creation.
 * This utility must not be used for runtime authentication and is not a replacement for BCrypt.
 * </p>
 *
 * Example:
 * Input:  password
 * Output: {SHA-256}{1}5787039480429368bf94732aacc771cd0a3ea02bcf504ffe1185ab94213bc63a
 */
public final class LegacyPasswordEncoder {

    private static final long DEFAULT_USER_ID = 1L;

    private LegacyPasswordEncoder() {
    }

    /**
     * Encodes a plain-text password for the default administrator account (ID: 1).
     *
     * @param password plain-text password to encode
     * @return formatted legacy password hash
     * @throws IllegalArgumentException if password is null, empty, or blank
     */
    public static String encode(String password) {
        return encodeForUser(password, DEFAULT_USER_ID);
    }

    /**
     * Internal extension point to encode a plain-text password for a specific user ID.
     *
     * @param password plain-text password to encode
     * @param userId user ID associated with the legacy salt/hash format
     * @return formatted legacy password hash
     * @throws IllegalArgumentException if password is null, empty, or blank
     */
    static String encodeForUser(String password, long userId) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password must not be null, empty, or blank");
        }

        String saltedInput = password + "{" + userId + "}";
        byte[] digest = computeSha256(saltedInput);
        String hexDigest = toHex(digest);

        return "{SHA-256}{" + userId + "}" + hexDigest;
    }

    private static byte[] computeSha256(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available in current JVM", e);
        }
    }

    private static String toHex(byte[] digest) {
        return HexFormat.of().formatHex(digest);
    }
}
