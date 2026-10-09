# Problem Statement

## 1. Title
AI Dataset Labeling Marketplace (LabelAI)

## 2. Domain
AI/ML data operations and annotation workflow management.

## 3. Who is the user?
- **Dataset Owner:** uploads source data and creates labeling projects.
- **Annotator:** receives tasks and submits labels.
- **Reviewer:** checks submitted labels and records approval/rejection remarks. In the current prototype, reviewer accounts must be provisioned rather than publicly self-selected.

## 4. What problem are we solving?
Machine-learning models require labeled data, but dataset owners often coordinate annotation work through disconnected files and messages. This makes task ownership, progress tracking, and label quality difficult to audit. LabelAI centralizes dataset metadata, projects, assignments, annotations, and review decisions. For text sentiment tasks, an explainable keyword-based baseline can suggest a starting label, but a human remains responsible for the final label.

## 5. Proposed Solution
- JWT-based account registration and login with BCrypt password hashing.
- Dataset file upload and metadata management.
- Project creation linked to a dataset and owner.
- Assignment of tasks to annotators.
- Annotation submission and review decisions with remarks.
- Dashboard summaries for workflow records.
- AI-assisted sentiment suggestion endpoint with confidence-like baseline score, rationale, and human-review flag.

## 6. Core Entities / Database Tables
1. `users` — identity, email, password hash, role.
2. `datasets` — title, description, file path, owner FK.
3. `projects` — project name/status, dataset FK, owner FK.
4. `tasks` — status, project FK, annotator FK.
5. `annotations` — label, task FK.
6. `reviews` — decision/remarks, annotation FK, reviewer FK.

## 7. User Roles & Permissions
- **Public:** register as OWNER or ANNOTATOR and log in.
- **Authenticated user:** access API resources after presenting a valid JWT. Write operations are role-restricted (OWNER/ADMIN for uploads, projects and task assignment; ANNOTATOR/OWNER/ADMIN for annotation; REVIEWER/ADMIN for reviews). Per-record ownership checks remain a hardening item before production use.
- **Reviewer/Admin:** role values exist in the model, but public registration cannot assign them. Provision these accounts manually for review demonstrations.

## 8. Success Criteria
- A user can register and log in and receives a signed JWT.
- Dataset metadata persists in MySQL after file upload.
- A project can be linked to a dataset.
- A task can be assigned to an annotator.
- A label can be submitted and reviewed with a recorded decision.
- The suggestion endpoint returns a label, rationale, score, and human-review flag for non-empty text.
- Backend tests and frontend production build pass in CI.

## 9. Out of Scope
- Payment processing and paid annotator marketplace transactions.
- Claims of medical/legal/high-stakes model accuracy.
- A production-trained NLP model in this iteration.
- Fully granular multi-tenant authorization and enterprise audit compliance.
- Object-storage integration and large dataset streaming.

## 10. Chosen Track
Java (Spring Boot 3.x) backend with React frontend and MySQL database.
