# LabelAI Deployment Guide — Review-II / Review-III

## 1. Managed MySQL
Create a MySQL 8 database on a managed provider. Set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` as backend environment variables. Keep the URL in JDBC format, for example `jdbc:mysql://HOST:3306/labelai?useSSL=true&serverTimezone=UTC` (use the provider's exact connection details).

## 2. Backend on Render
The repository includes `render.yaml` and `backend/Dockerfile`. Create a Render web service from the repository and configure the variables below. Render's local filesystem may be ephemeral; the current upload implementation writes to disk, so use object storage or a persistent disk before relying on uploaded files in production.

Required variables:
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET` — random secret at least 32 bytes; never commit it
- `JWT_EXPIRATION_MS` — e.g. `3600000`
- `FRONTEND_ORIGIN` — exact deployed frontend origin, e.g. `https://your-app.vercel.app`

Health check: `/api/health`  
Swagger UI: `/swagger-ui/index.html`

## 3. Frontend on Vercel / Netlify
Set the frontend root directory to `frontend`, build command to `npm run build`, and output directory to `dist`. Set `VITE_API_URL` to the deployed backend base URL ending in `/api`, e.g. `https://YOUR-BACKEND.onrender.com/api`.

After deployment, set `FRONTEND_ORIGIN` in the backend to the exact frontend origin and redeploy the backend. Avoid wildcard CORS origins.

## 4. GitHub Actions CI/CD
- `backend.yml` runs `./mvnw -B clean verify` and can trigger a Render deploy hook after successful checks.
- `frontend.yml` installs npm dependencies and runs `npm run build`.
- Add repository secret `RENDER_DEPLOY_HOOK` only after the Render deploy hook has been created.

## 5. Final verification
Open the frontend from a separate browser/device, register/login, upload a small file, create a project, assign a task, submit a label, and review it. Confirm data persistence, Swagger, health check, and green GitHub Actions. Replace placeholders in README and review checklist with actual live URLs. Do not claim deployment is complete until those checks pass.
