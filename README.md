# Book Library Application (Spring Boot)

Run the application from:  
`LibraryApplication.java`

## API Documentation (Swagger)

After starting the project, access the API documentation here:

http://localhost:8080/swagger-ui/index.html

## Database Access

### H2 Console

Access the embedded database at:

http://localhost:8080/h2-console

**JDBC URL:**
```
jdbc:h2:file:./data/library_database
```

---

## REST API Endpoints (Postman)

### Users
```
GET http://localhost:8080/users
```

### Books
```
GET http://localhost:8080/books
```

### Reservations
```
GET http://localhost:8080/reservations
```

### Example (Custom Exception Test)
```
GET http://localhost:8080/users/20
```
> (Triggers exception since user ID 20 does not exist)

---

## Example Requests

### Create Book
```
POST http://localhost:8080/books
```

```json
{
  "title": "DotinProject",
  "author": "Maryam",
  "available": true,
  "pages": 635,
  "user": null
}
```

---

### Reserve Book (Google Calendar Integration)

```
POST http://localhost:8080/users/13/reserve-book?bookName=DotinProject
```

---

## Google Calendar Integration

Reservations are automatically synced with Google Calendar.

### UI Screenshot

![Screenshot](https://github.com/user-attachments/assets/d690004f-5068-4214-ba96-be4bdee5a81a)

### Setup Requirement

Download and add the Google Calendar API JAR:

https://mvnrepository.com/artifact/com.google.apis/google-api-services-calendar/v3-rev20220715-1.32.1

---

## Project Features

- 3-Tier Architecture
- RESTful API with Spring Boot
- Dependency Injection
- JPA + Entity Manager
- H2 Database
- MongoDB Integration
- Swagger UI Documentation
- Logging with SLF4J
- AOP-based Logging
- Custom Exceptions
- Validation
- Unit Testing
- Security (Authentication & Authorization)
- Google Calendar API Integration
- Interactive Terminal UI
- In-memory ArrayList database (legacy layer)

---

# Terminal-Based Version (httpClient.java)

This version provides a CLI-based interface after login and authorization.

Users are shown role-based menus:
- User
- Admin
- Manager

Each role has specific permissions and actions. The system exits after task completion or user selection.

---

## Authentication & Security

### Password Hashing (SHA-256 + Salt)

Passwords are never stored in plain text. Instead:

- Each user gets a unique cryptographic salt
- Passwords are hashed using SHA-256 + salt
- Only hashed values are stored in the database

---

### Login Flow

1. User enters username and password
2. System retrieves stored salt + hash
3. Input password is hashed with stored salt
4. Hashes are compared
   - Match → Login successful
   - No match → Access denied

---

## User Module

### Available Methods

- `showAvailableBooks()`  
  Displays all books available for reservation.

- `reservationRequest(Scanner in)`  
  Requests a book reservation.

- `pendingReserveBooks()`  
  Shows pending reservation requests.

- `deleteReserveRequest(Scanner in)`  
  Cancels a pending reservation.

- `showReservedBooks()`  
  Displays approved reservations.

- `getBookNameFromUser(Scanner in)`  
  Helper method for input handling.

- `showFilteredBooks(String stat)`  
  Internal helper for filtering reservations.

- `showMenu(Scanner in)`  
  Main user menu navigation.

---

## Admin Module

The `Admin` class extends `User` and provides system management features:

- `addBook()` → Add new books
- `removeBook()` → Delete books by ID
- `showMenu(Scanner in)` → Admin dashboard menu
- `runFuncForCommand(int choice, Scanner in)` → Executes admin actions

---
