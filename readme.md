# 📊 User Authentication & Budget System

## 🚀 Overview
This project handles **user registration, authentication, role management, budget assignments, and category linking** using **Spring Boot, Hibernate (JPA), and Spring Security**.

## 🔗 **Component Relationships**

### **1️⃣ User Registration Flow (`UserLogic` + `UserService`)**
📌 When a **new user registers**, the system:
1. **Encodes** the password before saving.
2. **Assigns a role** (`USER` by default or custom role).
3. **Ensures required categories exist** (`General Expenses`).
4. **Creates a default budget** linked to the user and category.


✅ **User data is stored in the database using JPA repositories.**

---

### **2️⃣ Login Flow (`AuthService`)**
📌 When a **user logs in**, the system:
1. **Retrieves user details** from the database.
2. **Checks the hashed password** using `passwordEncoder.matches()`.
3. If valid, authentication is successful.


✅ **Authentication does NOT modify user data.**

---

### **3️⃣ Database Relationships (`Entities`)**
📌 The system consists of **four main entities**:

✅ **Each user gets assigned a role, a default category, and a budget.**

---

## **📜 API Endpoints**
| HTTP Method | Endpoint | Description |
|-------------|----------------|----------------------------|
| **POST** | `/api/users/register` | 📝 Registers a new user |
| **POST** | `/api/auth/login` | 🔐 Authenticates a user |
| **GET** | `/api/users/{username}` | 🔍 Fetches user details |
| **POST** | `/api/budgets/create` | 📊 Assigns budget to a user |

---

## **🛠 Dependencies**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **H2 Database (for testing)**
- **BCrypt (for password hashing)**

---

## **🔗 Full Flow Diagram**


---

## **✅ Summary**
✔ **`UserLogic` handles user creation, budget assignment, and category setup.**  
✔ **`AuthService` handles login verification but NOT user creation.**  
✔ **`UserService` connects controllers to the database layer.**  
✔ **Each user has a role, a budget, and categories assigned on creation.**

---

Would you like to include **SQL schema** or **sample API requests** in the `README.md`? 🚀
