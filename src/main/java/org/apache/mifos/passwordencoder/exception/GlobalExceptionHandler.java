package org.apache.mifos.passwordencoder.exception;

import org.apache.mifos.passwordencoder.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Centralized REST exception handler.
 * <p>
 * Converts {@link IllegalArgumentException} into HTTP 400 Bad Request responses
 * while keeping controllers free of exception handling logic.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentException thrown by the password encoding engine.
     *
     * @param exception the caught IllegalArgumentException
     * @return ResponseEntity containing ErrorResponse and HTTP 400 Bad Request status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage()));
    }
}
