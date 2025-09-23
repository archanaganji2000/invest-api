### Prerequisites

- Java 21
- A database  MySQL running locally 
-  Gradle installed 

bash
# build
./gradlew clean build

# run
./gradlew bootRun



The app will start (by default) at: `http://localhost:8080`

### Environment Variables

Configure in `application.yml` or as env vars:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ark
    username: ark_user
    password: ark_password
  jpa:
    hibernate:
      ddl-auto: update   # or validate / create / create-drop
    show-sql: true
server:
  port: 8080
```

---

## Domain Model

- **Fund**: investment pool; has many `Transaction`s
- **Investor**: client who invests; can invest in many funds
- **Transaction**: links `Investor` ↔ `Fund`, with:
  - `date` (ISO-8601)
  - `amount` (BigDecimal)
  - `type` ∈ { `CONTRIBUTION`, `INTEREST_INCOME`, `DISTRIBUTION`, `GENERAL_EXPENSE`, `MANAGEMENT_FEE` }
  - optional `note`

---

## API Reference

> All routes are JSON-based. Set `Content-Type: application/json` for requests with bodies.

### Funds

Base path: `/api/funds`

| Method | Path                 | Description                            | Notes |
|-------:|----------------------|----------------------------------------|-------|
| POST   | `/save`              | Create a fund                          | Returns `201 Created` with `Location` (inferred) |
| GET    | `/`                  | List all funds                         | — |
| GET    | `/{id}`              | Get fund by id                         | — |
| PATCH  | `/{id}`              | Partially update a fund                | Partial fields only |
| PUT    | `/{id}`              | Full update (uses `UpdateRequest`)     | Full replacement (inferred) |
| DELETE | `/{id}`              | Delete a fund                          | `204 No Content` on success |


### Investors

Base path: `/api/investors`

| Method | Path                 | Description                            | Notes |
|-------:|----------------------|----------------------------------------|-------|
| POST   | `/save`              | Create an investor                      | Returns `201 Created` with `Location` (inferred) |
| GET    | `/`                  | List all investors                      | — |
| GET    | `/{id}`              | Get investor by id                      | — |
| PATCH  | `/{id}`              | Partially update an investor            | — |
| DELETE | `/{id}`              | Delete an investor                      | `409 Conflict` if investor has transactions |

### Transactions

Base path: `/api/transactions`

| Method | Path                 | Description                            | Notes |
|-------:|----------------------|----------------------------------------|-------|
| POST   | `/save`              | Create a transaction                    | Validates fund & investor exist |
| GET    | `/`                  | List transactions                       | Add pagination if needed |
| GET    | `/{id}`              | Get transaction by id                   | — |
| PUT    | `/{id}`              | Full update (body: `UpdateRequest`)     | Returns updated `TransactionRequest` |
| DELETE | `/{id}`              | Delete a transaction                    | `204 No Content` |

### Reports

Base path: `/api/reports`

| Method | Path                               | Description                  |
|-------:|------------------------------------|------------------------------|
| GET    | `/funds/{fundId}/summary`          | Summary for a fund           |
| GET    | `/investors/{investorId}/summary`  | Summary for an investor      |


- **Status codes:**
  - `200 OK` — successful reads/updates
  - `201 Created` — successful POST (with `Location` header)
  - `204 No Content` — successful DELETE
  - `400 Bad Request` — invalid payload/arguments (`InvalidArgumentException`/`IllegalArgumentException`)
  - `404 Not Found` — missing resource (`NotFoundException`)
  - `409 Conflict` — business rule violation (e.g., deleting investor with existing transactions via `ConflictException`)

