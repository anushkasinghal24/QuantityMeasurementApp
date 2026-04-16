# Quantity Measurement App

A simple Spring Boot based application for measuring, comparing, converting, and performing arithmetic on quantities like length, weight, volume, and temperature.

## What This Project Does

- Compare two quantities
- Add, subtract, and divide compatible quantities
- Convert values between supported units
- Store and manage quantity history
- Secure APIs with JWT and Google OAuth login

## Merged UC Summary

These are the earlier branch features that were merged into `main`, explained in a short and simple way:

- UC1: Feet equality
- UC2: Inch equality
- UC3: Generic length support
- UC4: Yard equality
- UC5: Unit-to-unit conversion
- UC6: Quantity addition
- UC7: Addition in target unit
- UC8: Standalone unit support
- UC9: Weight measurement support
- UC10: Generic quantity class
- UC11: Volume measurement support
- UC12: Subtraction and division
- UC13: Centralized arithmetic logic
- UC14: Temperature measurement support
- UC15: N-tier architecture
- UC16: Database integration
- UC17: Spring Boot framework integration
- UC18: Google OAuth authentication

## UC21 Microservices Architecture

This project also includes the UC21 microservices setup. The application is split into small services so each part can be developed and deployed independently.

### Services

| Service | Purpose | Default Port |
|---------|---------|--------------|
| `eureka-server` | Service registry for all microservices | `8761` |
| `api-gateway` | Single entry point for all client requests | `8080` |
| `auth-service` | Login, JWT, and Google OAuth authentication | `8082` |
| `quantity-service` | Quantity operations and conversions | `8081` |
| `history-service` | Stores and returns operation history | `8083` |

### Request Flow

1. The client sends requests to `api-gateway`.
2. The gateway forwards requests to the correct microservice.
3. Each service registers itself with `eureka-server`.
4. `auth-service` handles authentication and token generation.
5. `quantity-service` handles measurement logic.
6. `history-service` stores operation history.

### Main Routes

- `/api/auth/**` -> `auth-service`
- `/api/quantity/**` -> `quantity-service`
- `/api/history/**` -> `history-service`

## Tech Stack

- Java 21
- Spring Boot 3.2.5
- Spring Security
- Spring Data JPA
- JWT
- Google OAuth2
- Eureka Server
- Spring Cloud Gateway
- MySQL
- H2 for local testing in the monolith module
- Maven

## Project Layout

```text
QuantityMeasurementApp/
|-- src/                  # Main Spring Boot application
|-- api-gateway/          # Gateway service
|-- auth-service/         # Auth microservice
|-- eureka-server/        # Service registry
|-- history-service/      # History microservice
|-- quantity-service/     # Quantity microservice
|-- spring-boot-temp/     # Old/sample project files
|-- test.http             # API test requests
```

## How To Run

### Prerequisites

- Java 21
- Maven 3.6+
- MySQL

### Start Order

1. Start `eureka-server`
2. Start `quantity-service`
3. Start `auth-service`
4. Start `history-service`
5. Start `api-gateway`

### Build

```bash
mvn clean install
```

### Run the main app

```bash
mvn spring-boot:run
```

## Important Notes

- Gateway default URL: `http://localhost:8080`
- Eureka dashboard: `http://localhost:8761`
- MySQL database name: `qma_db`
- Google OAuth is configured in `auth-service`

## Example API Calls

### Compare quantities

```bash
curl -X POST http://localhost:8080/api/quantity/compare \
  -H "Content-Type: application/json" \
  -d '{"q1":{"value":1,"unit":"FEET"},"q2":{"value":12,"unit":"INCH"}}'
```

### Add quantities

```bash
curl -X POST http://localhost:8080/api/quantity/add \
  -H "Content-Type: application/json" \
  -d '{"q1":{"value":5,"unit":"FEET"},"q2":{"value":6,"unit":"INCH"}}'
```

## API Overview

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | Login with username and password |
| GET | `/api/auth/google-success` | Google OAuth callback |
| POST | `/api/quantity/compare` | Compare two quantities |
| POST | `/api/quantity/add` | Add two quantities |
| POST | `/api/quantity/subtract` | Subtract quantities |
| POST | `/api/quantity/divide` | Divide quantities |
| POST | `/api/quantity/convert` | Convert between units |
| GET | `/api/quantity/all` | Get all stored measurements |
| GET | `/api/quantity/{id}` | Get one measurement by id |
| DELETE | `/api/quantity/{id}` | Delete a measurement |

## Files You May Want To Check

- `src/main/resources/application.properties`
- `api-gateway/target/classes/application.properties`
- `auth-service/target/classes/application.properties`
- `quantity-service/target/classes/application.properties`
- `history-service/target/classes/application.properties`
- `eureka-server/target/classes/application.properties`
- `test.http`

## Short Summary

This repository now has one clean README that explains the project in simple language and includes the UC21 microservices architecture details.
