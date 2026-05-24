<div align="center">

# Console Finance Tracker 💰

A console-based personal finance tracking application built using **Core Java**.

This project was created to practise Java concepts like **OOP**, **Collections**, **File Handling**, **Streams API**, **Exception Handling**, and basic **Multithreading** through a practical project.

Transaction data is stored in a CSV file so records remain saved even after closing the application.

<br>

![Java](https://img.shields.io/badge/Java-Core%20Java-orange?style=for-the-badge&logo=openjdk)
![CSV](https://img.shields.io/badge/Storage-CSV%20File-blue?style=for-the-badge)
![Streams](https://img.shields.io/badge/Java-Streams-green?style=for-the-badge)
![Status](https://img.shields.io/badge/Project-Active-success?style=for-the-badge)

</div>

---


---

## Project Purpose

This project was created as a **Core Java reinforcement project**.  
The main goal is not to build a production-level finance system, but to understand how Java concepts work together in a real application.

The project focuses on:

- Classes and objects
- Encapsulation
- Constructors, getters, and setters
- Enums
- ArrayList and List
- Scanner input handling
- Custom exceptions
- Java Streams
- File handling using CSV
- Service and controller separation
- Basic multithreading through autosave
- Console-based menu design

---

## Features

### Transaction Management

Users can:

- Add a new transaction
- View all transactions
- Find transaction by ID
- Update transaction details
- Update title, amount, type, date, and description
- Delete transaction option planned for future improvement

Each transaction contains:

- ID
- Title
- Amount
- Type: `INCOME` or `EXPENSE`
- Date
- Description

---

### Search and Filter

The application supports:

- Search by transaction type
- Search by date
- Search by amount range
- View transactions sorted by date
- View transactions sorted by amount
- Search by category planned for improvement

---

### Report Generation

The application can generate reports such as:

- Total income
- Total expense
- Current balance
- Highest income
- Highest expense
- Average income
- Average expense
- Monthly summary

The monthly summary groups transactions by month and shows:

```text
Month | Income | Expense | Balance
````

---

### File Handling

The project uses CSV file handling to store transaction data permanently.

When the program starts:

```text
transactions.csv is loaded
```

When the program exits:

```text
latest transactions are saved back into transactions.csv
```

This allows data to persist even after the application is closed.

The file is stored in:

```text
data/transactions.csv
```

---

### Autosave Using Multithreading

The project also includes a basic autosave feature using Java multithreading.

A background thread periodically saves the current transaction data while the main program continues running.

This helps practise:

* `ScheduledExecutorService`
* Background task execution
* Thread-safe transaction snapshot
* Autosaving data without blocking the main menu

---

## Technologies Used

* Java
* Core Java Collections
* Java Streams
* Java File I/O
* Java Time API
* Java Exception Handling
* Java Multithreading
* CSV file storage

No database, framework, or external library is used.
This project is intentionally kept as a **pure Core Java console application**.


---

## How to Run the Project

### 1. Clone the repository

```bash
git clone https://github.com/your-username/console-finance-tracker.git
```

Replace `your-username` with your actual GitHub username.

---

### 2. Open the project

Open the project in any Java IDE such as:

* IntelliJ IDEA
* Eclipse
* VS Code

---

### 3. Make sure Java is installed

Check your Java version:

```bash
java -version
```

This project can run on modern Java versions such as Java 17 or above.

---

### 4. Run the application

Run the `Main.java` file.

The console menu will appear:

```text
========================================
        CONSOLE FINANCE TRACKER
========================================
1. Add New Transaction
2. Transactions Menu
3. Search and Filter
4. Transaction Report
5. Exit
----------------------------------------
Enter your choice:
```

---

## CSV File Format

Transactions are saved in this format:

```csv
id,title,amount,type,date,description
1,Salary,50000.0,INCOME,2026-05-21,Monthly salary
2,Coffee,150.0,EXPENSE,2026-05-21,Morning coffee
```

The transaction type must be either:

```text
INCOME
EXPENSE
```

The date must be in this format:

```text
yyyy-MM-dd
```

Example:

```text
2026-05-21
```

---

## Example Usage

A user can add an income transaction:

```text
Title: Salary
Amount: 50000
Type: INCOME
Date: 2026-05-21
Description: Monthly salary
```

A user can also add an expense transaction:

```text
Title: Coffee
Amount: 150
Type: EXPENSE
Date: 2026-05-21
Description: Morning coffee
```

The report section can then calculate:

```text
Total Income
Total Expense
Current Balance
Monthly Summary
Highest Expense
Average Income
```

---

## Learning Outcome

By building this project, I practised how different Core Java concepts work together in one application.

The project helped me understand:

* How to separate controller and service logic
* How to store data using an ArrayList
* How to validate input using custom exceptions
* How to use Java Streams for reporting
* How to save and load data using files
* How to use multithreading for autosaving
* How to structure a small Java console application

---

## Future Improvements

Possible future improvements include:

* Add category-wise summary
* Improve CSV handling for commas in text
* Add JSON file storage
* Add database support using JDBC
* Add unit testing
* Convert the project into a Spring Boot REST API
* Add a simple frontend or desktop UI

---

## Project Status

This project is currently under development as a Core Java learning project.

The main purpose is to strengthen Java fundamentals before moving into advanced backend development using Spring Boot and databases.

