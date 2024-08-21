# Library

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

---
# **Persian Description**

# کتابخانه

## احراز هویت کاربر و هش کردن رمز عبور

### هش کردن رمز عبور در کلاس `User`

در این سیستم کتابخانه، احراز هویت کاربران به‌طور امن با هش کردن رمزهای عبور با استفاده از الگوریتم SHA-256 قبل از ذخیره‌سازی انجام می‌شود. این کار اطمینان می‌دهد که رمزهای عبور به‌صورت خام (متن ساده) هرگز در پایگاه داده ذخیره نمی‌شوند و خطر افشای رمز عبور کاهش می‌یابد.

### نحوه کار هش کردن رمز عبور

1. **تولید نمک (Salt)**:
    - قبل از هش کردن رمز عبور، برای هر کاربر یک نمک منحصربه‌فرد تولید می‌شود. نمک یک توالی تصادفی از بایت‌ها است که یک لایه اضافی از امنیت به فرآیند هش کردن اضافه می‌کند. این کار مانع از استفاده مهاجمان از جداول پیش‌محاسبه شده (مانند جداول رنگین‌کمانی) برای شکست هش می‌شود.
    - نمک با استفاده از کلاس `SecureRandom` تولید می‌شود که یک مقدار تصادفی قوی از نظر رمزنگاری ایجاد می‌کند. نمک تولید شده سپس با استفاده از Base64 به یک فرمت رشته‌ای رمزگذاری می‌شود تا به راحتی ذخیره شود.

2. **هش کردن رمز عبور**:
    - پس از تولید نمک، رمز عبور با استفاده از الگوریتم SHA-256 هش می‌شود. نمک با رمز عبور ترکیب شده و مقدار حاصل از طریق الگوریتم هش عبور داده می‌شود.
    - کلاس `MessageDigest` برای انجام عملیات هش کردن استفاده می‌شود. رمز عبور هش شده سپس به یک رشته Base64 رمزگذاری می‌شود که در کنار نمک ذخیره می‌شود.

3. **ذخیره و تأیید رمز عبور**:
    - در هنگام ثبت نام کاربر، سیستم نمک تولید کرده و رمز عبور کاربر را هش می‌کند. رمز عبور هش شده و نمک در شیء `User` (و به‌تبع آن، در پایگاه داده) ذخیره می‌شوند.
    - هنگامی که یک کاربر تلاش می‌کند وارد سیستم شود، سیستم رمز عبور وارد شده را با استفاده از همان نمک ذخیره شده برای آن کاربر هش می‌کند و سپس هش حاصل را با هش ذخیره شده مقایسه می‌کند تا رمز عبور تأیید شود.

### فرآیند ورود به سیستم

پس از ورود به سیستم، سیستم از کاربر می‌خواهد نام کاربری و رمز عبور خود را وارد کند. سیستم نمک و رمز عبور هش شده مرتبط با نام کاربری را بازیابی کرده، رمز عبور وارد شده را با همان نمک ذخیره شده هش کرده و دو هش را مقایسه می‌کند:

- اگر هش‌ها مطابقت داشته باشند، ورود به سیستم موفقیت‌آمیز است و کاربر به سیستم دسترسی پیدا می‌کند.
- اگر هش‌ها مطابقت نداشته باشند، ورود به سیستم شکست می‌خورد و از کاربر خواسته می‌شود دوباره تلاش کند.

این روش ذخیره‌سازی و تأیید رمز عبور تضمین می‌کند که حتی اگر پایگاه داده به خطر بیفتد، مهاجمان به رمزهای عبور متن ساده کاربران دسترسی نخواهند داشت.

___

## متدهای کلاس `User`

کلاس `User` در سیستم کتابخانه به کاربران امکان تعامل با سیستم رزرو کتاب کتابخانه را می‌دهد. در زیر توضیحی از متدهای کلیدی ارائه شده است:

### `showAvailableBooks()`
این متد فهرستی از تمام کتاب‌هایی که در حال حاضر برای رزرو موجود هستند را نمایش می‌دهد. وضعیت دسترسی هر کتاب در کتابخانه بررسی شده و شناسه کتاب، عنوان، نویسنده و تعداد صفحات آن چاپ می‌شود.

### `reservationRequest(Scanner in)`
این متد به کاربر امکان می‌دهد تا درخواست رزرو کتاب را ثبت کند. از کاربر خواسته می‌شود تا نام کتاب را وارد کند، وضعیت دسترسی کتاب بررسی می‌شود و در صورت موجود بودن، تلاش برای رزرو آن انجام می‌شود. اگر رزرو موفقیت‌آمیز باشد، پیامی تأییدی نمایش داده می‌شود؛ در غیر این صورت، به کاربر اطلاع داده می‌شود که درخواست رزرو به دلیل رزرو بودن کتاب شکست خورده است.

### `pendingReserveBooks()`
این متد فهرستی از تمام کتاب‌هایی که کاربر درخواست رزرو آن‌ها را داده اما هنوز تأیید نشده‌اند را نمایش می‌دهد. درخواست‌های رزرو کاربر فیلتر شده و تنها درخواست‌های با وضعیت "در انتظار تأیید" را نشان می‌دهد، که شامل شناسه رزرو، شناسه کتاب و عنوان هر کتاب در انتظار تأیید است.

### `deleteReserveRequest(Scanner in)`
این متد به کاربر امکان می‌دهد تا درخواست رزرو در انتظار تأیید را حذف کند. از کاربر خواسته می‌شود تا نام کتابی که می‌خواهد رزرو آن را لغو کند وارد کند. اگر درخواست رزروی که با نام وارد شده مطابقت دارد پیدا شود و متعلق به کاربر باشد، رزرو حذف می‌شود. در صورت حذف موفقیت‌آمیز، پیامی تأییدی نمایش داده می‌شود.

### `showReservedBooks()`
این متد تمام کتاب‌هایی را که کاربر با موفقیت رزرو کرده است را نشان می‌دهد. درخواست‌های رزرو کاربر فیلتر شده و تنها درخواست‌های با وضعیت "تأیید شده" را نشان می‌دهد، که شامل شناسه رزرو، شناسه کتاب و عنوان هر کتاب رزرو شده است.

### `getBookNameFromUser(Scanner in)`
این یک متد کمکی است که از کاربر می‌خواهد نام کتاب را وارد کند. نام وارد شده برای پردازش بیشتر توسط متدهای دیگر بازگردانده می‌شود.

### `متد کمکی اضافی:`
- `showFilteredBooks(String stat)`: این متد کمکی کتاب‌ها را بر اساس وضعیت رزرو ارائه شده فیلتر کرده و نمایش می‌دهد (`"pending"` یا `"approved"`). از این متد در هر دو `pendingReserveBooks()` و `showReservedBooks()` استفاده می‌شود تا از تکرار کد جلوگیری شود.

### ناوبری منو:
- `showMenu(Scanner in)`: این متد منوی اصلی را به کاربر نمایش می‌دهد و به او اجازه می‌دهد تا از بین اقدامات موجود (مانند مشاهده کتاب‌های موجود، انجام رزرو) یکی را انتخاب کند. پس از انتخاب کاربر، متد مربوطه فراخوانی می‌شود. سپس از کاربر پرسیده می‌شود که آیا مایل است به استفاده از سیستم ادامه دهد یا خیر.
