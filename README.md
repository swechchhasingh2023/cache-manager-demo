# Cache Manager Demo

A full-stack Spring Boot + React project demonstrating a database-backed cache manager flow using Java, Spring Boot, JPA, PostgreSQL, Flyway, React, Docker, and Jenkins.

## Project Structure

```text
cache-manager-demo/
│
├── backend/
│   └── cache-manager-backend/
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/
│       │   │   │   └── com/example/cachemanager/
│       │   │   │       ├── cache/
│       │   │   │       ├── config/
│       │   │   │       ├── controller/
│       │   │   │       ├── dao/
│       │   │   │       ├── entity/
│       │   │   │       └── service/
│       │   │   └── resources/
│       │   │       └── db/migration/
│       │   └── test/
│       └── pom.xml
│
├── frontend/
│   └── cache-manager-frontend/
│       ├── src/
│       ├── public/
│       ├── package.json
│       └── vite.config.js
│
├── database/
├── Jenkinsfile
└── README.md
```

## Application Architecture

```text
                    React Frontend
                          |
                          | HTTP
                          ↓
                Spring Boot Backend
                          |
                          ↓
                     Controller
                          |
                          ↓
                       Service
                          |
                    ┌─────┴─────┐
                    ↓           ↓
                Cache HIT    Cache MISS
                    ↓           ↓
                 Return       DAO
                    data         ↓
                              EntityManager
                                  ↓
                              PostgreSQL
                                  ↓
                              CacheManager
                                  ↓
                               Response
```

## Technology Stack

### Backend

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate / JPA
- PostgreSQL
- Flyway
- Maven

### Frontend

- React
- Vite
- JavaScript
- HTML
- CSS
- ESLint

### DevOps / Tools

- Git
- GitHub
- Docker
- Jenkins
- Postman

## Backend Components

### Controller

**PromotionController**

Exposes REST APIs for:

- Creating promotions
- Reading promotions
- Removing promotions from cache
- Clearing the cache

### Service

**PromotionService**

Contains the application flow:

1. Check cache
2. Return cached data on cache hit
3. Query database on cache miss
4. Store database result in cache
5. Return response

### Cache Manager

**CacheManager**

Uses `ConcurrentHashMap` for in-memory caching.

Supported operations:

- `put()`
- `get()`
- `remove()`
- `clear()`

### DAO

**PromotionDao**

Uses JPA `EntityManager` to communicate with PostgreSQL.

### Entity

**Promotion**

Represents the `promotion` database table.

## Frontend

The frontend is a React application created using Vite.

The UI provides:

- Promotion ID input
- Promotion name input
- Save Promotion
- Get Promotion
- Remove From Cache
- Clear Cache
- API response display

### Frontend → Backend Flow

```text
React UI
   ↓
fetch()
   ↓
Spring Boot REST API
   ↓
PromotionController
   ↓
PromotionService
   ↓
CacheManager / DAO
   ↓
PostgreSQL
```

### CORS

The backend allows requests from the local React development server:

```text
http://localhost:5173
```

CORS configuration is implemented in:

```text
backend/cache-manager-backend/src/main/java/com/example/cachemanager/config/CorsConfig.java
```

## Database

PostgreSQL database:

```text
cache_demo
```

Table:

```text
promotion
```

### Flyway Migration

```text
V1__create_promotion_table.sql
```

The database schema is managed through Flyway migrations.

Hibernate schema validation is enabled so that the application validates the existing database schema instead of automatically modifying it.

## API Endpoints

### Create Promotion

```text
POST /promotions
```

Request:

```json
{
  "id": 103,
  "name": "Postman Sale"
}
```

### Get Promotion

```text
GET /promotions/{id}
```

Example:

```text
GET /promotions/103
```

### Remove Promotion From Cache

```text
DELETE /promotions/{id}
```

This removes the promotion from the in-memory cache.

It does **not** delete the database record.

### Clear Entire Cache

```text
DELETE /promotions/cache
```

This removes all entries from the in-memory cache.

## Cache Behaviour

### Cache Hit

If the promotion exists in the cache:

```text
Request
   ↓
CacheManager
   ↓
Cache HIT
   ↓
Response
```

No database query is required.

### Cache Miss

If the promotion is not present in the cache:

```text
Request
   ↓
CacheManager
   ↓
Cache MISS
   ↓
DAO
   ↓
PostgreSQL
   ↓
CacheManager
   ↓
Response
```

The database result is stored in the cache for subsequent requests.

## Local Backend Setup

Navigate to:

```bash
cd backend/cache-manager-backend
```

Run the application:

```bash
./mvnw spring-boot:run
```

Backend application:

```text
http://localhost:8080
```

## Local Frontend Setup

Navigate to:

```bash
cd frontend/cache-manager-frontend
```

Install dependencies:

```bash
npm install
```

Start the React development server:

```bash
npm run dev
```

Frontend application:

```text
http://localhost:5173
```

The backend should be running on port `8080` before testing frontend API operations.

## Testing

The REST APIs can be tested using Postman.

Example:

```text
POST    /promotions
GET     /promotions/{id}
DELETE  /promotions/{id}
DELETE  /promotions/cache
```

The same APIs can also be tested through the React frontend.

## Git

The project uses a single Git repository at the project root:

```text
cache-manager-demo/
```

Both backend and frontend are maintained in the same repository.

## CI/CD

The project will use Jenkins for automated build and CI/CD processing.

Docker will be used to containerize the applications.

Planned CI/CD flow:

```text
Developer
   ↓
Git
   ↓
GitHub
   ↓
Jenkins
   ↓
Build
   ↓
Test
   ↓
Docker Image
   ↓
Deployment
```

Detailed Docker, Jenkins, automated testing, and deployment setup will be documented as the project evolves.
