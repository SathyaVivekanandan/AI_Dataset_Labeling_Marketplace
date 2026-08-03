# Software Requirements Specification (SRS)

# Project Title
AI Dataset Labeling Marketplace

## 1. Introduction

### 1.1 Purpose
The purpose of this project is to develop a web-based platform where dataset owners can upload datasets, create labeling projects, assign annotation tasks, and review labeled data efficiently.

### 1.2 Scope
The system provides a centralized platform for dataset management and annotation. It supports multiple user roles including Admin, Dataset Owner, Annotator, and Reviewer.

---

## 2. Functional Requirements

### FR1 - User Registration
Users should be able to register using their name, email, password, and role.

### FR2 - User Login
Registered users should be able to log in securely.

### FR3 - Dataset Upload
Dataset owners can upload datasets for annotation.

### FR4 - Project Creation
Dataset owners can create labeling projects.

### FR5 - Task Assignment
Dataset owners can assign labeling tasks to annotators.

### FR6 - Data Annotation
Annotators can label assigned datasets.

### FR7 - Review Process
Reviewers can approve or reject submitted annotations.

### FR8 - Dashboard
Each user role has a separate dashboard.

---

## 3. Non-Functional Requirements

- Secure user authentication
- Responsive user interface
- Fast data retrieval
- Role-based access control
- Reliable database storage

---

## 4. User Roles

### Admin
- Manage users
- Monitor projects
- Generate reports

### Dataset Owner
- Upload datasets
- Create projects
- Assign tasks

### Annotator
- View assigned tasks
- Label datasets
- Submit annotations

### Reviewer
- Review submitted labels
- Approve or reject annotations

---

## 5. Technology Stack

Frontend:
- HTML
- CSS
- JavaScript
- Bootstrap

Backend:
- Java Spring Boot

Database:
- MySQL

Version Control:
- Git & GitHub

---

## 6. Expected Outcome

A working Minimum Business Product (MBP) that supports dataset upload, project management, annotation workflow, review process, and secure user authentication.