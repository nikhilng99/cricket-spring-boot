# Cricket API

A RESTful backend API for managing cricket players and teams, built with Spring Boot 4.

## Tech Stack
- Java 21
- Spring Boot 4
- Spring Data JPA
- PostgreSQL
- MapStruct
- Bean Validation
- JUnit 5 + Mockito
- Docker

## Features
- Full CRUD for Players and Teams
- DTO pattern with MapStruct — entities never exposed in API responses
- Bean Validation on all request bodies
- Global exception handling with meaningful error responses
- @OneToMany / @ManyToOne JPA relationship between Team and Player
- N+1 problem fixed using LEFT JOIN FETCH
- Pagination and sorting on GET /players
- Custom JPQL queries for filtering players by team and runs
- Unit tests for service layer with Mockito

## Running Locally

### Prerequisites
- Docker Desktop
- Java 21
- Maven

### Steps
1. Clone the repository
   git clone https://github.com/nikhilng99/cricket-spring-boot

2. Start PostgreSQL with Docker
   docker-compose up -d

3. Run the application
   ./mvnw spring-boot:run

4. Access Swagger UI
   http://localhost:8080/swagger-ui.html

## API Endpoints

### Players
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/v1/players | Get all players (paginated) |
| GET | /api/v1/players/{id} | Get player by ID |
| POST | /api/v1/players | Create a new player |
| PUT | /api/v1/players/{id} | Update player |
| DELETE | /api/v1/players/{id} | Delete player |
| GET | /api/v1/players/team/{teamId} | Get players by team |
| GET | /api/v1/players/team/{teamId}/min-runs/{minRuns} | Get players by team with minimum runs |

### Teams
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/v1/teams | Get all teams with players |
| GET | /api/v1/teams/{id} | Get team by ID |
| POST | /api/v1/teams | Create a new team |
| PUT | /api/v1/teams/{id} | Update team |

## Project Structure
src/main/java/com/cricket/api/cricketapi/
├── controller/    # REST controllers
├── service/       # Business logic
├── repository/    # Spring Data JPA repositories
├── entity/        # JPA entities
├── dto/           # Data Transfer Objects
├── mapper/        # MapStruct mappers
└── exception/     # Custom exceptions and global handler

## Commands to use:
1. docker-compose up -d
2. docker exec -it image_name bash
3. psql -U username
4. \l
5. CREATE DATABASE player;
6. add in VM options:  -Duser.timezone=UTC
