# Cache Manager Demo

A Spring Boot backend project demonstrating a database-backed cache manager flow using Java, Spring Boot, JPA, PostgreSQL, and Flyway.

## Project Structure

```text
cache-manager-demo/
├── backend/
│   └── cache-manager-backend/
├── frontend/
├── database/
├── Jenkinsfile
└── README.md

Backend Architecture
HTTP Request
     ↓
Controller
     ↓
Service
     ↓
CacheManager
     ├── Cache HIT
     │      ↓
     │   Return data
     │
     └── Cache MISS
            ↓
           DAO
            ↓
        EntityManager
            ↓
        PostgreSQL
            ↓
       CacheManager
            ↓
         Response
Technology Stack
Java 17
Spring Boot
Spring Web
Spring Data JPA
Hibernate / JPA
PostgreSQL
Flyway
Maven
Docker
Jenkins
Postman
Backend Components
Controller

PromotionController

Exposes REST APIs for creating promotions, reading promotions, and managing the cache.

Service

PromotionService

Contains the application flow:

Check cache
Return cached data on cache hit
Query database on cache miss
Store database result in cache
Return response
Cache Manager

CacheManager

Uses ConcurrentHashMap for in-memory caching.

DAO

PromotionDao

Uses JPA EntityManager to communicate with PostgreSQL.

Entity

Promotion

Represents the promotion database table.

Database

PostgreSQL database:

cache_demo

Table:

promotion

Flyway migration:

V1__create_promotion_table.sql
API Endpoints
Create Promotion
POST /promotions

Request:

{
  "id": 103,
  "name": "Postman Sale"
}
Get Promotion
GET /promotions/{id}

Example:

GET /promotions/103
Remove Promotion From Cache
DELETE /promotions/{id}

This removes the promotion from the in-memory cache. It does not delete the database record.

Clear Entire Cache
DELETE /promotions/cache
Cache Behaviour
Cache Hit

If the promotion exists in the cache:

Request
  ↓
CacheManager
  ↓
Cache HIT
  ↓
Response

No database query is required.

Cache Miss

If the promotion is not present in the cache:

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
Local Backend Setup

Navigate to:

cd backend/cache-manager-backend

Run the application using Maven:

./mvnw spring-boot:run

Application:

http://localhost:8080
Testing

The REST APIs can be tested using Postman.

Example:

POST    /promotions
GET     /promotions/{id}
DELETE  /promotions/{id}
DELETE  /promotions/cache
CI/CD

The project will use Jenkins for automated build and CI/CD processing.

Docker will be used to containerize the backend application.
Detailed Docker and Jenkins setup will be documented as the project evolves.
