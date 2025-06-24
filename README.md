# 🛒 E-Commerce Microservices Architecture with Spring Boot & JWT

This project is a basic **e-commerce system** built using **microservices architecture**. It demonstrates secure communication between services using **JWT** authentication and **Spring Cloud** technologies such as **Eureka** and **Feign Clients**.

---

## 📦 Microservices Included

- 🔐 **Auth Service** – Handles user login, registration, and JWT token generation
- 🧾 **Product Service** – Adds products linked to categories and subcategories
- 🗂️ **Category Service** – Manages product categories
- 📁 **Subcategory Service** – Manages product subcategories
- 🧭 **Eureka Discovery Server** – Service registration and discovery

---

## 🛠 Technologies Used

- Java 17
- Spring Boot
- Spring Cloud (Eureka, Feign)
- Spring Security with JWT
- MySQL
- Lombok
- REST APIs

---

## 🧱 Service Overview

### 🔐 Auth Service

- User registration and login
- Returns a **JWT token** after successful login
- Other services extract user identity from this token

---

### 📦 Product Service

- Endpoint: `POST /product/add`
- Requires a **valid JWT token**
- Accepts `categoryId` and `subCategoryId` in the request body
- Validates:
  - Whether the subcategory belongs to the selected category
- Associates the product with the **user's email**, extracted from the JWT token

---

### 🗂️ Category Service

- Add and retrieve product categories
- Sample endpoints:
  - `POST /category/add`
  - `GET /category/{id}`

---

### 📁 Subcategory Service

- Add and retrieve subcategories
- Each subcategory contains a `categoryId` to indicate its parent category
- Sample endpoint:
  - `GET /subcategory/{id}` returns:
```json
{
  "id": 5,
  "name": "Men Perfumes",
  "categoryId": 1
}
