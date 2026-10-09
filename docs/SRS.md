# Software Requirements Specification (SRS)

## 1. Introduction

### 1.1 Purpose
LabelAI is a full-stack dataset labeling workflow application for dataset owners, annotators, and reviewers.

### 1.2 Scope
The system provides account registration/login, dataset file upload, project creation, task assignment, annotation submission, review decisions, dashboard summaries, and explainable keyword-based text sentiment suggestions. The current suggestion service is a prototype baseline, not a trained ML model.

## 2. Functional Requirements

- **FR1 — Registration:** public registration supports OWNER and ANNOTATOR roles with input validation and BCrypt password hashing.
- **FR2 — Login:** valid credentials return a signed JWT; invalid credentials return 401.
- **FR3 — Dataset upload:** owners/admins upload a file and save dataset metadata.
- **FR4 — Project creation:** owners/admins create a project linked to a dataset.
- **FR5 — Task assignment:** owners/admins assign tasks to annotators.
- **FR6 — Annotation:** annotators submit labels against task records.
- **FR7 — Review:** reviewers/admins record approval/rejection/needs-changes decisions and remarks.
- **FR8 — Dashboard:** show dataset/project/task/review counts and recent records.
- **FR9 — AI suggestion:** authenticated users request a keyword-based POSITIVE/NEGATIVE/NEUTRAL suggestion with rationale and human-review flag.
- **FR10 — API documentation:** Swagger/OpenAPI describes the REST API.

## 3. Non-Functional Requirements
- JWT authentication and BCrypt password hashing.
- Role-based write permissions for major workflows.
- REST API responses use a consistent success/data/message envelope.
- MySQL persistence through Spring Data JPA/Hibernate.
- Responsive React frontend.
- Environment-based database and JWT configuration.
- Automated backend unit tests and frontend production build in GitHub Actions.

## 4. User Roles
- **Admin:** user provisioning/deletion and administrative write operations.
- **Dataset Owner:** upload datasets, create projects, assign tasks, and submit labels.
- **Annotator:** view task list and submit labels.
- **Reviewer:** review labels and record quality decisions. Reviewer/admin accounts are not publicly self-registered and must be provisioned deliberately.

## 5. Technology Stack
- Frontend: React 18, Vite, Axios, Lucide React, responsive CSS.
- Backend: Java, Spring Boot 3.5.5, Spring Security, Spring Data JPA.
- Database: MySQL 8.
- Auth: JWT and BCrypt.
- API docs: springdoc-openapi / Swagger UI.
- Tests: JUnit 5, Mockito.
- CI/CD: GitHub Actions.

## 6. Limitations and Expected Outcome
The project is a capstone prototype. Uploaded files currently use local disk storage, so cloud hosting needs persistent/object storage for durable uploads. Full row-level annotation/export, per-record ownership isolation, trained-model evaluation, and production deployment verification remain follow-up work. Public cloud URLs, screenshots, demo video, mentor review sign-offs, and real GitHub commit history must be added after they actually exist.
