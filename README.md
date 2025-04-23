# 🏢 SC2002 Group Project HDB BTO Management System

A Java-based Housing Development Board (HDB) Management System designed to simulate real-world HDB workflows. This system includes role-based access and functionality for Managers, Officers, and Applicants, and supports operations like project management, flat applications, officer approvals, and more.

---

## 📖 Description

This Java-based system simulates HDB's BTO (Build-To-Order) management workflow with role-based access for **Applicants**, **HDB Officers**, and **HDB Managers**. It showcases object-oriented programming, file-based data persistence, and user interaction via a console UI.

### 👤 Applicant
- View open projects based on marital status and age.
  - Singles (≥35 y/o): Only 2-Room.
  - Married (≥21 y/o): 2-Room or 3-Room.
- Apply to one project only; view application status: Pending, Successful, Unsuccessful, or Booked.
- Book flat (via Officer) upon successful application.
- Request withdrawal of application (before/after booking).
- Submit, view, edit, and delete enquiries.

### 🧑‍💼 HDB Officer
- Inherits all applicant functionalities.
- Register to be officer of one project (not during application to the same project or if handling another).
- Requires approval by the Manager.
- View and manage flat bookings (update inventory, book flats for applicants).
- Respond to enquiries and generate receipts post-booking.

### 🧑‍💼 HDB Manager
- Create, edit, delete BTO projects (with flat types, unit counts, application window, visibility).
- Approve/reject applications and officer registrations.
- Approve/reject withdrawal requests.
- Toggle project visibility and view all projects (including those by others).
- Generate filtered reports on flat bookings (e.g., by marital status or flat type).
- View and respond to enquiries for the project they're managing.

The system ensures data persistence through file storage and adheres to SOLID principles using clearly defined packages for UI, controllers, models, and interfaces.

---

## 🏁 Getting Started

### ✅ Prerequisites

- Java 17 or higher
- A Java IDE (e.g., IntelliJ IDEA, Eclipse)

### 🚀 Installation & Run

Clone this repository and run the main.java:
```bash
git clone https://github.com/your-username/SC2002-Group-Project-HDB-BTO
Open the project in your Java IDE.
```

Make sure the required serializable files are placed in the correct directory

Run the main.java class to start the application.

### ⚙️ Usage
Upon launching:

Login using NRIC and password given on NTULearn SC2002 Assignment CSV files.

The system automatically identifies your role and display avaiiable capabilities through menu tables

### 📂 Project Structure

Each package follows the MVC (Model-View-Controller) design pattern:
- **Entity** represents the models (data and domain logic).
- **Boundary** handles the user interface and input/output.
- **Controller** manages user commands and business logic.
- **Interface** ensures loosely coupled components using abstractions.
- **Serializer** handle data persistence through serialized files.
- **Database** stores entity data for easy access during runtime.
- **ENUM** adds clean, readable constants for states and types.

### 📦 Dependencies
Java 17 or higher


### ✨ Additional Features
🔐 Password Change Functionality
Users can change their password through the UI menu after logging in.

🖥️ Enhanced UI with Tabular Displays
Clear and user-friendly display of data such as project listings, application statuses, and user info using console tables.

💾 Data Persistence
All changes (projects, applications, user credentials, bookings) are automatically saved back to serializable files to preserve data across sessions.

### 👨‍💻 Authors
####  Satitmannaitham Napatr
#### TAHSEEN NAZIR	
#### TARUN ILANGOVAN	
#### TO WEN JUN	
#### WANG QUANZHI	
#### WOO JIA ERN




