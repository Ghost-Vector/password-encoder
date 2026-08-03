package org.apache.passwordencoder.cli;

import org.apache.passwordencoder.service.PasswordEncodingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Command line runner providing CLI execution capability for password encoding.
 * <p>
 * Delegates encoding requests directly to {@link PasswordEncodingService} when
 * command-line arguments are provided.
 * </p>
 */
@Component
public class PasswordEncoderCliRunner implements CommandLineRunner {

    private final PasswordEncodingService passwordEncodingService;

    public PasswordEncoderCliRunner(PasswordEncodingService passwordEncodingService) {
        this.passwordEncodingService = passwordEncodingService;
    }

    @Override
    public void run(String... args) {
        if (args.length == 0) {
            return;
        }

        if (args.length == 2 && "encode".equalsIgnoreCase(args[0])) {
            try {
                String encodedPassword = passwordEncodingService.encodePassword(args[1]);
                System.out.println(encodedPassword);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Usage: java -jar password-encoder.jar encode <password>");
        }
    }
}
