# mifos-password-encoder

A standalone Java 21 utility for generating legacy Apache Fineract-compatible password hashes.

## Overview

`mifos-password-encoder` generates legacy password hashes formatted specifically for historical Apache Fineract database scripts during manual tenant creation. 

Older tenant provisioning scripts require password hashes encoded in the `{SHA-256}{1}<hexDigest>` format. This utility reproduces that specific encoding logic cleanly without pulling in Apache Fineract dependencies.

> **Note**: This utility is strictly for database initialization and manual tenant setup. It is not intended for runtime authentication, user login validation, or as a replacement for BCrypt.

## Features

- **Legacy SHA-256 Encoding**: Generates `{SHA-256}{1}<hexDigest>` hashes matching legacy Fineract standards.
- **REST API**: HTTP endpoint for service integrations.
- **Command Line Interface (CLI)**: Direct terminal execution for scripting and ad-hoc generation.
- **Docker Support**: Multi-stage Docker container supporting both REST and CLI modes.
- **Java 21 & Spring Boot 3.x**: Built using modern Java records and Spring Boot best practices.
- **Zero Fineract Dependency**: Operates as a completely independent utility.

## Architecture

```
REST API                             CLI
   │                                  │
   ▼                                  ▼
PasswordController         PasswordEncoderCliRunner
   │                                  │
   └────────────────┬─────────────────┘
                    ▼
         PasswordEncodingService
                    │
                    ▼
          LegacyPasswordEncoder
                    │
                    ▼
           Legacy SHA-256 Hash
```

Both the REST controller and CLI runner share the exact same underlying service layer (`PasswordEncodingService`), ensuring consistent validation and encoding behavior across both entry points.

## Prerequisites

- **Java**: JDK 21 or higher
- **Maven**: Version 3.9+
- **Docker** (optional): For containerized execution

## Build

To compile and package the application into an executable JAR:

```bash
mvn clean package
```

The compiled binary will be located at `target/mifos-password-encoder-0.0.1-SNAPSHOT.jar`.

## Running as a REST API

Start the application without arguments to launch the web server:

```bash
java -jar target/mifos-password-encoder-0.0.1-SNAPSHOT.jar
```

By default, the REST server binds to port **8080**.

### API Endpoint

**`POST /api/v1/password/encode`**

#### Request

```http
POST /api/v1/password/encode HTTP/1.1
Content-Type: application/json

{
    "password": "password"
}
```

#### Response (HTTP 200 OK)

```json
{
    "encodedPassword": "{SHA-256}{1}5787039480429368bf94732aacc771cd0a3ea02bcf504ffe1185ab94213bc63a"
}
```

## Running as a CLI

Supply command-line arguments to execute the application in CLI mode without starting the web server.

### Command Format

```bash
java -jar target/mifos-password-encoder-0.0.1-SNAPSHOT.jar encode <password>
```

### Example Usage

```bash
java -jar target/mifos-password-encoder-0.0.1-SNAPSHOT.jar encode password
```

#### Output

```text
{SHA-256}{1}5787039480429368bf94732aacc771cd0a3ea02bcf504ffe1185ab94213bc63a
```

If invalid arguments are supplied, the application displays:

```text
Usage: java -jar mifos-password-encoder.jar encode <password>
```

## Running with Docker

### Build Docker Image

```bash
docker build -t mifos-password-encoder .
```

### REST Mode

```bash
docker run -p 8080:8080 mifos-password-encoder
```

### CLI Mode

```bash
docker run --rm mifos-password-encoder encode password
```

## Error Handling

### REST API

Invalid request payloads (e.g. `null`, empty, or blank passwords) return **HTTP 400 Bad Request**:

```json
{
    "message": "Password must not be null, empty, or blank"
}
```

### CLI

Invalid password input in CLI mode displays an error message:

```text
Error: Password must not be null, empty, or blank
```

## Project Structure

```text
src/main/java/org/apache/mifos/passwordencoder/
├── PasswordEncoderApplication.java
├── cli/
│   └── PasswordEncoderCliRunner.java
├── controller/
│   └── PasswordController.java
├── dto/
│   ├── ErrorResponse.java
│   ├── PasswordRequest.java
│   └── PasswordResponse.java
├── exception/
│   └── GlobalExceptionHandler.java
├── service/
│   └── PasswordEncodingService.java
└── util/
    └── LegacyPasswordEncoder.java
```

### Package Responsibilities

- `cli`: Handles command-line execution and argument processing.
- `controller`: Exposes HTTP REST endpoints for password encoding.
- `dto`: Contains immutable data transfer objects (`PasswordRequest`, `PasswordResponse`, `ErrorResponse`).
- `exception`: Manages centralized REST exception handling and HTTP error mappings.
- `service`: Orchestrates password encoding operations between entry points and the encoding engine.
- `util`: Implements core SHA-256 legacy password hashing logic using standard Java libraries.

## Design Decisions

- **Java 21 Records**: Used for immutable DTOs to eliminate boilerplate code.
- **Constructor Injection**: Ensures immutability and testability for components.
- **Service Layer**: Decouples HTTP and CLI entry points from core encoding rules.
- **Standalone Utility**: Keeps dependencies minimal with no Apache Fineract framework overhead.
- **No Third-Party CLI Framework**: Uses native Spring Boot `CommandLineRunner` to avoid unnecessary external dependencies.

## Future Improvements

Possible future enhancements include:

- Support for additional legacy hashing algorithms.
- OpenAPI / Swagger documentation.
- Automated unit and integration test suites.
- CI/CD build pipeline integration.

## License

License information can be added according to your organization's requirements.
