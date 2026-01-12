# 🎓 Student Management System (Spring Boot)

Welcome to the **Student Management System**, a comprehensive backend API built during the **"Spring Boot: Beginner to Job Ready"** YouTube series. This project demonstrates how to build a production-grade RESTful service using Java 21 and the latest Spring ecosystem.

## 📺 Course Context

This repository is the official code companion for our YouTube playlist. We move from the absolute basics of Spring Boot to advanced concepts like JWT security, relational mapping, and performance optimization.

---

## 🛠 Tech Stack

* **Language:** Java 21 (LTS)
* **Framework:** Spring Boot 3.x
* **Database:** PostgreSQL / MySQL (Hibernate JPA)
* **Security:** Spring Security & JWT
* **Documentation:** Swagger UI / Open API
* **Build Tool:** Maven

---

## ✨ Features

### 1. Core Modules

* **Student Management:** Full CRUD, pagination, and advanced search (name/email).
* **Course & Department:** Organizes academic hierarchy; includes a dashboard API for entity counts.
* **Instructor Tracking:** Manage teaching assignments and departmental links.
* **Enrollment System:** Handles the many-to-many relationship between students and courses.

### 2. Advanced Logic

* **Performance Tracking:** Add marks and generate student report cards.
* **Validation & Integrity:** Prevents accidental deletion of departments containing active students.
* **Security:** Role-based access (Admin/Viewer) using JWT tokens.

### 3. Bonus Features

* **File Handling:** Profile photo uploads.
* **Exports:** Generate CSV/PDF lists for students.
* **Notifications:** Email triggers for enrollment confirmation.

---

## 🗺 Database Schema

The project uses a relational database design to handle complex academic connections.

---

## 🚀 Getting Started

### Prerequisites

* JDK 21
* Maven 3.8+
* Your favorite IDE (IntelliJ IDEA recommended)
* PostgreSQL (or your preferred SQL database)

### Installation

1. **Clone the repository:**
```bash
git clone https://github.com/your-username/university-management-system.git

```


2. **Configure Database:**
Update `src/main/resources/application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/university_db
spring.datasource.username=your_username
spring.datasource.password=your_password

```


3. **Run the application:**
```bash
mvn spring-boot:run

```



---

## 📖 API Documentation

Once the application is running, you can explore the API endpoints via Swagger UI:
`http://localhost:8080/swagger-ui.html`

---

## 📝 Roadmap & Progress

* [x] Basic CRUD for Students
* [x] Department & Instructor logic
* [ ] JWT Authentication & Authorization
* [ ] Student Performance Analytics (In progress...)
* [ ] Email Notifications (Upcoming...)
