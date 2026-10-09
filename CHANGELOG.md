# Changelog

## 3.0.0 — Capstone enhancement and review readiness
- Migrated the frontend to React 18 + Vite with responsive workspace screens.
- Added explainable keyword-based sentiment suggestions and unit tests.
- Removed hardcoded database credentials from Spring configuration and moved JWT signing key/lifetime to environment variables.
- Prevented password serialization and restricted public registration to OWNER/ANNOTATOR roles.
- Added OpenAPI/Swagger dependency, diagrams, review checklists, deployment notes, and full README sections.
- Added React production build workflow and updated backend Docker/Render configuration.

## 2.0.0 — Existing application baseline
- Spring Boot API for authentication, datasets, projects, tasks, annotations, and reviews.
- MySQL persistence, JWT security, and initial frontend prototype.
