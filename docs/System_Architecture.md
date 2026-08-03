# System Architecture

## Project Name
AI Dataset Labeling Marketplace

## Architecture Overview

The system follows a three-layer architecture:

1. Presentation Layer
- User interface for interacting with the system.
- Provides login, dashboard, dataset upload and annotation screens.

2. Business Logic Layer
- Handles user authentication.
- Manages dataset projects.
- Controls task assignment and annotation workflow.

3. Data Layer
- Stores user details, datasets, tasks, annotations and reviews.
- Uses MySQL database.

## Technology Stack

Frontend:
- HTML
- CSS
- JavaScript
- Bootstrap

Backend:
- Java
- Spring Boot

Database:
- MySQL

AI Module:
- Python

## System Flow

User → Frontend → Spring Boot Backend → MySQL Database

AI Module communicates with backend for future AI-based annotation features.