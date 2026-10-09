# Review-II Checklist — Full Product and Deployment

- [ ] Backend starts with environment variables and connects to managed MySQL.
- [ ] Frontend is deployed and `VITE_API_URL` points to the backend.
- [ ] Backend host CORS permits only the actual frontend origin(s).
- [ ] Live URLs below open successfully from a separate network/browser.
- [ ] Register/login returns JWT; invalid password is rejected.
- [ ] Dataset upload and metadata persist across backend restart (requires durable/object storage in production).
- [ ] Project, task, annotation, and review flows demonstrated end-to-end.
- [ ] `/api/health` and Swagger UI are reachable.
- [ ] Backend unit tests pass; frontend production build passes.
- [ ] GitHub Actions checks are green on the final commit.
- [ ] No secrets are committed; hosting environment variables are set.
- [ ] README contains actual live URLs and real screenshots.

Frontend URL: ____________________
Backend URL: ____________________
Swagger URL: ____________________
Review date: ____________________
