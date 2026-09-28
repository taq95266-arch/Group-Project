# Home Car Services App 🚗

## Project Overview

**Home Car Services App** is a web-based platform designed to make car maintenance and roadside services easier and more convenient for car owners.

The application allows users to request **car maintenance, towing, and car washing services** directly at their home or another selected location. Users can select their car, choose the required service, and provide their location so that a technician can come directly to them.

The platform also provides **communication with technicians**, allowing users to send pictures, videos, or audio recordings when they are unsure about a car problem. An **AI-powered assistant** can also help users understand dashboard warning lights and guide them toward the appropriate service.

> **"Your Garage Comes to You."**

---

## 🏗️ Architecture Overview

The Home Car Services App follows a **three-layer architecture**:

```text
┌──────────────────────────────┐
│          Frontend            │
│      User Interface          │
│                              │
│ Forms / Services / Requests  │
└──────────────┬───────────────┘
               │
               │ REST API
               ▼
┌──────────────────────────────┐
│           Backend            │
│        Java + Spring Boot    │
│                              │
│ Controllers                  │
│ Services                     │
│ Repositories                 │
│ Security / Authentication    │
│ AI Assistant                 │
└──────────────┬───────────────┘
               │
               │ JPA / Hibernate
               ▼
┌──────────────────────────────┐
│           Database           │
│            MySQL             │
│                              │
│ Users                        │
│ Cars                         │
│ Services                     │
│ Service Requests             │
│ Technicians                  │
│ Bookings                     │
│ Locations                    │
└──────────────────────────────┘
```

---

## Main Components

| Component        | Responsibility                                                       |
| ---------------- | -------------------------------------------------------------------- |
| **Frontend**     | User interface, forms, service selection, and service requests       |
| **Controllers**  | Handle HTTP requests and responses                                   |
| **Services**     | Business logic and service processing                                |
| **Repositories** | Database access using Spring Data JPA                                |
| **Security**     | Authentication and user access control                               |
| **AI Assistant** | Identifies dashboard warning lights and provides simple explanations |
| **MySQL**        | Stores users, cars, services, bookings, and related information      |

---

## Main System Flow

```text
User
 ↓
Frontend
 ↓
Select Car
 ↓
Select Service
 ↓
Provide Location
 ↓
REST API
 ↓
Controller
 ↓
Service
 ↓
Repository
 ↓
MySQL
```

### AI Assistant Flow

```text
User
      ↓
Dashboard Warning Light
      ↓
Take / Upload Picture
      ↓
Frontend
      ↓
AI REST Endpoint
      ↓
AI Assistant
      ↓
Warning Light Identification
      ↓
Simple Explanation
      ↓
Recommended Service
      ↓
Service Request
```

The AI assistant provides guidance and **does not replace professional mechanical diagnosis**.

---

## 🛠️ Main Features

### 1. Home Car Maintenance

Users can request a technician to provide maintenance services directly at their location.

Services can include:

* Engine oil changes
* Filter replacement
* Battery replacement
* Consumable parts replacement
* Regular car maintenance

The technician comes to the user's selected location instead of requiring the user to visit a garage.

### 2. Towing Service

When a car breaks down and cannot be driven to a garage, users can request a **tow truck** through the application.

The vehicle can then be transported to a garage.

### 3. Car Washing

Users can book a **car washing service** at their home or another selected location.

### 4. Communication with Technicians

Users can communicate with technicians when they are unsure about a problem with their car.

They can send:

* 📷 Pictures
* 🎥 Videos
* 🎙️ Audio recordings

For example, a user can record an unusual engine sound and send it to a technician. The technician can review the recording and explain possible causes before the user books a service.

### 5. AI Assistant

The application includes an AI assistant that helps users understand **dashboard warning lights**.

Users can take a picture of a warning light and upload it to the AI assistant. The AI identifies the warning symbol and explains its meaning in simple language.

For example:

```text
User uploads:
Tire Pressure Warning Light

        ↓

AI Assistant

        ↓

"This is the tire pressure warning light.
You should check your tire pressure."

        ↓

Request Appropriate Service
```

The AI is intended to provide guidance and does not replace a professional mechanical diagnosis.

---

## 🛠️ Technology Stack

| Layer               | Technology                  |
| ------------------- | --------------------------- |
| **Frontend**        | HTML, CSS, JavaScript       |
| **Backend**         | Java, Spring Boot           |
| **API**             | REST API                    |
| **AI**              | AI Assistant                |
| **Database**        | MySQL                       |
| **ORM**             | Spring Data JPA / Hibernate |
| **Authentication**  | Role-Based Access Control   |
| **Testing**         | JUnit / Spring Boot Test    |
| **Version Control** | Git & GitHub                |

---

## 📂 Project Structure

```text
Home-Car-Services-App/
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/homecarservices/
│   │   │   │   ├── controller/
│   │   │   │   ├── service/
│   │   │   │   ├── repository/
│   │   │   │   ├── entity/
│   │   │   │   ├── dto/
│   │   │   │   ├── security/
│   │   │   │   └── ai/
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   │
│   └── pom.xml
│
├── frontend/
│   ├── index.html
│   ├── css/
│   ├── js/
│   └── assets/
│
├── database/
│   ├── schema.sql
│   ├── seed.sql
│   └── erd/
│
├── docs/
│   └── API.md
│
└── README.md
```

---

## 🚀 Setup

### Prerequisites

Install the following:

* **Java 17+**
* **Maven**
* **MySQL 8+**
* **Git**
* Modern web browser
* **Postman** *(optional, for API testing)*

---

### 1. Clone the Repository

```bash
git clone <repository-url>
cd Home-Car-Services-App
```

---

### 2. Create the Database

Open MySQL and create the database:

```sql
CREATE DATABASE home_car_services;
```

Run the database scripts if provided:

```sql
SOURCE database/schema.sql;
SOURCE database/seed.sql;
```

---

### 3. Configure the Backend

Open:

```text
backend/src/main/resources/application.properties
```

Configure the MySQL connection:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/home_car_services
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Configure the required environment variables:

```text
DB_USERNAME=your_username
DB_PASSWORD=your_password
AI_API_KEY=your_api_key
```

> **Do not commit passwords or API keys to GitHub.**

---

### 4. Run the Backend

```bash
cd backend
mvn spring-boot:run
```

The Spring Boot application will start locally.

---

### 5. Run the Frontend

Open the `frontend` folder using a local development server.

For example, using VS Code **Live Server**, open:

```text
frontend/index.html
```

The frontend communicates with the Spring Boot backend through REST APIs.

---

## 🧪 Testing

Run backend tests using:

```bash
cd backend
mvn test
```

Tests can cover important application functions such as:

* User authentication
* Car management
* Home maintenance requests
* Towing requests
* Car washing bookings
* Technician communication
* AI assistant functionality

---

## 📡 API Documentation

The REST API provides endpoints for the main system modules:

```text
/api/auth
/api/users
/api/cars
/api/services
/api/technicians
/api/maintenance
/api/towing
/api/car-washing
/api/communication
/api/ai
```

Detailed API documentation is available in:

```text
docs/API.md
```

---

## 👥 Team

| Role                           | Member                                                                      |
| ------------------------------ | --------------------------------------------------------------------------- |
| **Team Leader / Focal Person** | Mohammed AlBalushi                                                          |
| **Team Members**               | Taqwa AlHinai, Abdulaziz Mohammed,** Intisar Said,** Abdulrhman Al-Gheilani |
| **Supervisor**                 | Fatma AlMamari & Is'haq AlBalushi                                           |

---

## 📌 Project Status

🚧 **In Development**

### Project Goal

The goal of the Home Car Services App is to make car maintenance **easier, faster, and more convenient**.

Instead of driving to a garage and waiting for service, users can request a technician to come directly to their location. The application combines **home car maintenance, towing services, car washing, technician communication, and AI assistance** in one platform.
