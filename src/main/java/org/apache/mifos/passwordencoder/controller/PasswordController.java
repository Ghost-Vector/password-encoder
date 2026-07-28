package org.apache.mifos.passwordencoder.controller;

import org.apache.mifos.passwordencoder.dto.PasswordRequest;
import org.apache.mifos.passwordencoder.dto.PasswordResponse;
import org.apache.mifos.passwordencoder.service.PasswordEncodingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller exposing REST endpoints for generating legacy password hashes.
 * <p>
 * This controller delegates encoding operations directly to {@link PasswordEncodingService}
 * and intentionally contains no business logic, validation, or hashing logic.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/password")
public class PasswordController {

    private final PasswordEncodingService passwordEncodingService;

    public PasswordController(PasswordEncodingService passwordEncodingService) {
        this.passwordEncodingService = passwordEncodingService;
    }

    /**
     * Encodes a plain-text password into the legacy Mifos password hash format.
     *
     * @param request the password request containing the plaintext password
     * @return response containing the generated legacy password hash
     */
    @PostMapping("/encode")
    public PasswordResponse encodePassword(@RequestBody PasswordRequest request) {
        String encodedPassword = passwordEncodingService.encodePassword(request.password());
        return new PasswordResponse(encodedPassword);
    }
}
