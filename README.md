# Customer Club Application

This is a customer club management application built using **Java 17**, **Spring Boot 3**, and **PostgreSQL**. The
application provides REST APIs for user registration, login, provider integration, and score management. Users can
accumulate points from activities in third-party provider applications and redeem them for products offered by the club.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Development](#development)
- [Contributing](#contributing)

## Features

- **Authentication**: Token-based authentication system with user registration and login APIs.
- **Admin Functionality**:
    - Register providers (other apps).
    - Define products with score costs for users to purchase.
    - Providers can add scores to user accounts via a secure API using their access token.
- **User Accounts**:
    - A unique account is created for each user for every provider, and scores are accumulated in these accounts.
- **Product Management**: Admins can add products with a score cost, and users can redeem their scores to purchase these
  products.

## Tech Stack

- **Java 17**
- **Spring Boot 3**
- **PostgreSQL** database
- **H2** integration test database
- **Swagger** for API documentation
- **Docker Compose** for containerized deployment

## Getting Started

### Prerequisites

- **Java 17**
- **Docker** and **Docker Compose**
- **PostgreSQL** (if not using Docker)

### Clone the Repository

```bash
git clone https://github.com/Glb71/club.git
cd club
```

### Configuration

The application is configured using environment variables in the `application.yml` file. Default values are set, but
they can overridden through environment variables:

```yaml
server:
  port: 8080
spring:
  datasource:
    url: jdbc:postgresql://${DATABASE_HOST:postgres}:${DATABASE_PORT:5432}/${DATABASE_NAME:snapp_club}
    username: ${DATABASE_USER_NAME:snapp}
    password: ${DATABASE_PASSWORD:123456}
application:
  jwt:
    token-expiration-second: ${SEC_TOKEN_EXP_DURATION:9000}
    secret-key: ${SEC_TOKEN_SECRET:myJWTSecretKey_2024!#%&()=+?@^_|~}
```

### Running the Application

You can run the application using Docker Compose:

```bash
docker compose up
```

this will start the application on port `8080` with a PostgreSQL database.

### Swagger API Documentation

Once the application is running, you can access the API documentation via Swagger at:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Development

### Building the Application

You can build the application using Maven:

```bash
mvn clean package
```

### Running Tests

```bash
mvn test
```

### Running Integration Tests

```bash
mvn verify
```

### Running with a Local Database

If you prefer to run the application with a local PostgreSQL database instead of Docker, update the `application.yml` or
set the appropriate environment variables for the database connection.

## Contributing

Contributions are welcome! If you have suggestions, ideas, or find any bugs, feel free to open an issue or submit a pull
request.

### How to Contribute

1. Fork the repository.
2. Create a new feature branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a pull request.