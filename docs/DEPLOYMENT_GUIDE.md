# LabelAI Review-II Deployment Guide

## 1. Cloud MySQL
Create a MySQL 8 database with these values available as environment variables:
- `DB_URL` – JDBC connection URL
- `DB_USERNAME`
- `DB_PASSWORD`

The application uses `spring.jpa.hibernate.ddl-auto=update`, so JPA can create/update the related tables on first startup.

## 2. Backend
The included `backend/Dockerfile` builds a Java 17 container from the Maven-generated JAR.

Build locally:
```bash
cd backend
mvn clean test package
```

Run locally:
```bash
mvn spring-boot:run
```

Required production variables:
```text
DB_URL=...
DB_USERNAME=...
DB_PASSWORD=...
JWT_SECRET=<long random secret>
JWT_EXPIRATION_MS=3600000
PORT=8080
```

## 3. Swagger
After deployment, open `/swagger-ui/index.html`.

## 4. Frontend
Set `window.LABELAI_API_URL` before `app.js` loads, for example:
```html
<script>window.LABELAI_API_URL = "https://YOUR-BACKEND-URL";</script>
```
Then serve the `frontend` folder with any static hosting provider.

## 5. GitHub Actions
`.github/workflows/ci.yml` runs backend build/tests and frontend syntax checks on push and pull request.

## 6. Account-specific final step
The actual public URL and cloud credentials cannot be embedded in this ZIP. After deployment, record the URLs in `README.md` and `docs/REVIEW_2_CHECKLIST.md`.
