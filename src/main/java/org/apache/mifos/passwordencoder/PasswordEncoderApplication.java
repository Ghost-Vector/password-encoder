package org.apache.mifos.passwordencoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application entry point for the Mifos Password Encoder Spring Boot application.
 */
@SpringBootApplication
public class PasswordEncoderApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PasswordEncoderApplication.class);
        if (args.length > 0) {
            app.setWebApplicationType(WebApplicationType.NONE);
        }
        app.run(args);
    }
}
