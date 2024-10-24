# Book Library Application (run LibraryApplication.java)

For REST API endpoints and program documentation, run the program and open http://localhost:8080/swagger-ui/index.html

Checkout the h2 database at http://localhost:8080/h2-console with JDBC URL set to "jdbc:h2:file:./data/library_database"

Commands in postman:

GET localhost:8080/users

GET localhost:8080/books

GET localhost:8080/reservations

Checkout custom exceptions with command GET GET localhost:8080/users/20 (since we don't have any user with id set to 20)

For UI:

POST localhost:8080/books

body:
{
    "title": "DotinProject",
    "author": "Maryam",
    "available": true,
    "pages": 635,
    "user": null
}

POST localhost8080/users/13/reserve-book

params -> bookName = DotinProject

Then, the reservations will be shown at google calendar

![Screenshot (613)](https://github.com/user-attachments/assets/d690004f-5068-4214-ba96-be4bdee5a81a)

### Google Calendar UI
download and add JAR file:
https://mvnrepository.com/artifact/com.google.apis/google-api-services-calendar/v3-rev20220715-1.32.1

What this project has:

Interactive Terminal, Arraylist Database, *H2 Database and JPA Repository*, MongoDB Database, Swagger, 3-tier Architecture, Dependency Injection, slf4j Log, *Log with AOP*, *Custom Exceptions*, Entity Manager, Security and Validation, Unit Test, UI Using Google Calendar API
---

# Library Application with terminal (httpClient.java)

After logging in and authorization, users are presented with a menu based on their role (User, Admin, or Manager). Each role has access to specific options in their menu, allowing them to perform actions appropriate to their role. Once their tasks are completed, they can exit the program by selecting the exit option

## User Authentication and Password Hashing

### Password Hashing in the `User` Class

In this library system, user authentication is handled securely by hashing passwords with the SHA-256 algorithm before storing them. This ensures that raw (plain-text) passwords are never stored in the database, reducing the risk of password exposure.

### How Password Hashing Works

1. **Salt Generation**:
    - Before hashing the password, a unique salt is generated for each user. The salt is a random sequence of bytes that adds an additional layer of security to the hashing process. This prevents attackers from using precomputed tables (like rainbow tables) to crack the hash.
    - The salt is generated using the `SecureRandom` class, which produces a cryptographically strong random value. The generated salt is then encoded into a string format using Base64 for easy storage.

2. **Password Hashing**:
    - Once the salt is generated, the password is hashed using the SHA-256 algorithm. The salt is combined with the password, and the resulting value is passed through the hashing algorithm.
    - The `MessageDigest` class is used to perform the hashing. The hashed password is then encoded into a Base64 string, which is stored alongside the salt.

3. **Storing and Verifying Passwords**:
    - During user registration, the system generates a salt and hashes the user's password. The hashed password and salt are stored in the `User` object (and by extension, in the database).
    - When a user attempts to log in, the system hashes the provided password using the same salt stored for that user. It then compares the resulting hash with the stored hash to verify the password.

### Login Process

Upon login, the system prompts the user to enter their username and password. The system retrieves the stored salt and hashed password associated with the username, hashes the entered password with the same salt, and compares the two hashes:

- If the hashes match, the login is successful, and the user is granted access to the system.
- If the hashes do not match, the login fails, and the user is prompted to try again.

This method of password storage and verification ensures that even if the database is compromised, the attackers would not have access to the user's plain-text passwords.
___

## User Class Methods

The `User` class in the library system allows users to interact with the library's book reservation system. Below is a description of the key methods:

### `showAvailableBooks()`
This method displays a list of all books that are currently available for reservation. It checks the availability status of each book in the library and prints out the book's ID, title, author, and number of pages.

### `reservationRequest(Scanner in)`
This method allows the user to request a reservation for a book. It prompts the user to enter the book's name, checks if the book is available, and if so, attempts to reserve it. If the reservation is successful, a confirmation message is displayed; otherwise, it informs the user that the reservation request failed because the book is already reserved.

### `pendingReserveBooks()`
This method displays a list of all books that the user has requested to reserve but are still pending approval. It filters the user's reservations to show only those with a "pending" status, listing the reservation ID, book ID, and title of each pending book.

### `deleteReserveRequest(Scanner in)`
This method allows the user to delete a pending reservation request. The user is prompted to enter the name of the book for which they want to cancel the reservation. If a matching reservation is found, and it belongs to the user, the reservation is deleted. A confirmation message is displayed upon successful deletion.

### `showReservedBooks()`
This method shows all books that the user has successfully reserved. It filters the user's reservations to show only those with an "approved" status, listing the reservation ID, book ID, and title of each reserved book.

### `getBookNameFromUser(Scanner in)`
This is a helper method that prompts the user to enter a book name. The entered name is then returned for further processing by other methods.

### `Additional Utility Method:`
- `showFilteredBooks(String stat)`: This helper method filters and displays books based on the reservation status provided (`"pending"` or `"approved"`). It is used by both `pendingReserveBooks()` and `showReservedBooks()` to avoid code duplication.

### Menu Navigation:
- `showMenu(Scanner in)`: This method displays the main menu to the user, allowing them to choose from the available actions (e.g., viewing available books, making a reservation). After the user selects an option, the corresponding method is called. The user is then asked if they wish to continue using the system.

___
### Admin Class

The `Admin` class extends the `User` class, providing additional capabilities for managing the library system:

- `addBook()`: Allows the admin to add new books to the library by entering the book's title and author.
- `removeBook()`: Enables the admin to remove books from the library by specifying the book's ID.
- `showMenu(Scanner in)`: Displays a menu for the admin to choose actions like viewing all books, adding a book, removing a book, or viewing all users.
- `runFuncForCommand(int choice, Scanner in)`: Executes the command selected from the menu, such as adding or removing a book.

