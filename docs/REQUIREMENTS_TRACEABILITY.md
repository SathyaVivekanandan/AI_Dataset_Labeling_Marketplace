# Capstone PDF Requirements Traceability

This is a code-package status, not a claim that cloud deployment or mentor reviews have already happened.

| Requirement from supplied capstone guide | Project artifact / implementation | Status before student-side verification |
|---|---|---|
| Problem statement with user types, entities, roles, criteria, out-of-scope | `docs/Problem_Statement.md` | Prepared |
| ≥5 related DB tables | 6 JPA entities + `database/schema.sql` | Implemented in code; verify MySQL startup |
| System architecture diagram + editable source | `docs/diagrams/architecture.png` + `.dot` | Prepared |
| ER diagram with PK/FK and relationships | `docs/ER_Diagram.png` + `docs/diagrams/er-diagram.dot` | Prepared |
| Class/module diagram | `docs/diagrams/modules.png` + `.dot` | Prepared |
| API contract / Swagger | springdoc dependency; `/swagger-ui/index.html` | Implemented in code; verify startup |
| JWT login, hashed passwords, role-aware write permissions | Security config, JWT filter, BCrypt, `@PreAuthorize` | Implemented in code; must run tests locally |
| React frontend | `frontend/src/main.jsx`, Vite config via package scripts | Implemented; npm build not run in this environment |
| Dataset upload, project/task/annotation/review flow | Controllers + React screens | Implemented; verify end-to-end with MySQL |
| AI enhancement + tests | `AiSuggestionService`, controller and unit tests | Implemented as keyword baseline, not trained ML |
| Unit tests and ≥40% service coverage target | UserService + AI suggestion tests | Added; run `mvnw clean verify` to confirm |
| GitHub Actions backend/frontend CI | `.github/workflows/backend.yml`, `frontend.yml` | Prepared; needs green GitHub run |
| Cloud-hosted frontend, backend, managed database | `render.yaml`, Dockerfile, deployment guide | **Pending user account setup and actual deployment** |
| Health check, environment-based secrets | `/api/health`, env properties | Implemented; verify deployed config |
| README exact sections, screenshots, demo links | `README.md` | Sections prepared; **live URLs/screenshots/video still need real values** |
| Review-I / Review-II / Review-III demos and sign-offs | `docs/reviews/` | Scripts/checklists prepared; reviews not performed here |
| ≥26 commits over 9 weeks and PR discipline | `docs/Commit_Tracker.md` | **Cannot be generated honestly by a ZIP; use actual GitHub activity** |
