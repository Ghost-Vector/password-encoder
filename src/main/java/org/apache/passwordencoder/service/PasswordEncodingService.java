package org.apache.passwordencoder.service;

import org.apache.passwordencoder.util.LegacyPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service providing password encoding operations.
 * <p>
 * This service acts as the orchestration layer between application entry points
 * (such as REST API and CLI) and the underlying encoding engine. It delegates encoding
 * operations directly to {@link LegacyPasswordEncoder} and intentionally contains no
 * hashing implementation or duplicated validation logic.
 * </p>
 */
@Service
public class PasswordEncodingService {

    /**
     * Encodes a plain-text password into the legacy password hash format.
     *
     * @param password the plain-text password to encode
     * @return the formatted legacy password hash string
     */
    public String encodePassword(String password) {
        return LegacyPasswordEncoder.encode(password);
    }
}
