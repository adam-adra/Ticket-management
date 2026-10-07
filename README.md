# Ticket Management REST API

An enterprise-grade, production-ready RESTful API built in **Java 21** and **Spring Boot** with **PostgreSQL** persistence. Designed according to clean layered architecture, contract decoupling via immutable DTO records, declarative Jakarta bean validation, centralized `@RestControllerAdvice` error handling, and interactive OpenAPI documentation.

---

## Quick Access & Interactive API Documentation

> [!TIP]
> With the application running on port **8000**, open the interactive Swagger UI directly in your browser to inspect schemas, explore endpoints, and execute live API calls:

```text
http://localhost:8000/swagger-ui/index.html
```

* **Raw OpenAPI Specification (JSON)**: `http://localhost:8000/v3/api-docs`

---

## Technology Stack

| Component | Technology | Description |
| :--- | :--- | :--- |
| **Language** | Java 21 LTS | Modern records, switch pattern matching, strong typing |
| **Framework** | Spring Boot | Dependency injection, autoconfiguration, embedded Tomcat |
| **Persistence** | Spring Data JPA / Hibernate | Object-Relational Mapping (ORM), dirty checking, connection pooling |
| **Database** | PostgreSQL 16 / 18 | Relational ACID database running on port `5432` |
| **Validation** | Jakarta Bean Validation | Declarative request payload validation (`@Valid`, `@NotBlank`, etc.) |
| **Boilerplate Reduction** | Project Lombok | Compile-time bytecode generation for getters, builders, and loggers |
| **Observability** | SLF4J / Logback | Leveled, structured logging (`@Slf4j`) |
| **API Documentation** | SpringDoc OpenAPI (Swagger UI) | Live, introspected interactive API documentation |
| **Build System** | Apache Maven Wrapper | `mvnw` / `mvnw.cmd` portable build toolchain |
| **Automation** | GNU Make | `Makefile` providing standardized shortcuts for operations |

---

## System Architecture: Strict Layered Decoupling

The application strictly separates responsibilities across three decoupled layers:

```
                  HTTP Requests (JSON)
                           │
                           ▼
┌──────────────────────────────────────────────────────────┐
│                   REST CONTROLLER LAYER                  │
│  - TicketController (/api/tickets)                       │
│  - Transport handling, HTTP Status codes (200, 201, 204) │
│  - ZERO business logic, ZERO database operations        │
└──────────────────────────┬───────────────────────────────┘
                           │ Passes DTO Records
                           ▼
┌──────────────────────────────────────────────────────────┐
│                   BUSINESS SERVICE LAYER                 │
│  - TicketService                                         │
│  - Business rules, transactions (@Transactional)         │
│  - Entity <---> DTO Record conversions                   │
└──────────────────────────┬───────────────────────────────┘
                           │ Passes JPA Entities
                           ▼
┌──────────────────────────────────────────────────────────┐
│                 PERSISTENCE REPOSITORY LAYER             │
│  - TicketRepository (extends JpaRepository)              │
│  - Spring Data JPA proxies, SQL query generation         │
└──────────────────────────┬───────────────────────────────┘
                           │ JDBC / TCP 5432
                           ▼
┌──────────────────────────────────────────────────────────┐
│                    POSTGRESQL DATABASE                   │
│  - Table: tickets (id, title, status, priority, etc.)    │
└──────────────────────────────────────────────────────────┘
```

### Contract Decoupling (Entities vs. DTO Records)
* **JPA Entity (`Ticket.java`)**: Models the PostgreSQL `tickets` table with Hibernate annotations. **Entities are never returned directly from controllers.**
* **DTO Records (`record`)**:
  * `TicketCreateRequest`: Client input when creating a ticket (no `id` or `createdAt` allowed).
  * `TicketUpdateRequest`: Client input when updating status, priority, title, or description.
  * `TicketResponse`: Immutable output payload sent back to clients via `TicketResponse.fromEntity(...)`.

---

## REST API Endpoints

Base URL: `http://localhost:8000/api/tickets`

| Method | Endpoint | Description | Success Status | Error Statuses |
| :--- | :--- | :--- | :---: | :---: |
| **GET** | `/api/tickets` | Retrieve all tickets (or paginated slice) | `200 OK` | `500` |
| **GET** | `/api/tickets/{id}` | Retrieve a specific ticket by its ID | `200 OK` | `404 Not Found` |
| **POST** | `/api/tickets` | Create a new ticket (initial status: `OPEN`) | `201 Created` | `400 Bad Request` |
| **PUT** | `/api/tickets/{id}` | Update title, description, status, or priority | `200 OK` | `400`, `404` |
| **DELETE** | `/api/tickets/{id}` | Permanently delete a ticket | `204 No Content` | `404 Not Found` |

---

## Validation & Centralized Error Handling

All incoming requests are guarded by Jakarta Bean Validation (`@Valid`).

If a client sends invalid data or requests a non-existent ID, our `@RestControllerAdvice` (`GlobalExceptionHandler`) intercepts the event and returns a uniform JSON error envelope:

### Resource Not Found (`404 Not Found`)
```json
{
  "timestamp": "2026-10-07T15:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Ticket not found with id: 999",
  "path": "/api/tickets/999",
  "fieldErrors": null
}
```

### Validation Error (`400 Bad Request`)
```json
{
  "timestamp": "2026-10-07T15:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/tickets",
  "fieldErrors": {
    "title": "Title is required",
    "priority": "Priority is required (LOW, MEDIUM, HIGH)"
  }
}
```

---

## How to Run the Project

### 1. Start the Database
You can start PostgreSQL using either **Make** or **Docker Compose**:

* **Via Makefile (Scoop Local PostgreSQL)**:
  ```bash
  make db-start
  ```
* **Via Docker Compose**:
  ```bash
  docker compose up -d
  ```

### 2. Launch the Spring Boot Server
```bash
make run
```
*(or via Maven Wrapper: `./mvnw spring-boot:run` / `.\mvnw.cmd spring-boot:run`)*

### 3. Open the Interactive Swagger UI
Open your browser and navigate to:
```text
http://localhost:8000/swagger-ui/index.html
```

### 4. Run Automated Test Suite
```bash
./mvnw test
```

---
