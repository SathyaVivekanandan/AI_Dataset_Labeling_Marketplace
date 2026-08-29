# LabelAI – Review-II Checklist

## Product
- [x] Spring Boot backend
- [x] MySQL/JPA persistence
- [x] React/static frontend
- [x] JWT authentication foundation
- [x] BCrypt password hashing
- [x] Dataset, project, task, annotation and review APIs
- [x] Health endpoint
- [x] Swagger/OpenAPI dependency
- [ ] Cloud database credentials configured
- [ ] Backend public URL configured
- [ ] Frontend public URL configured

## Quality
- [x] JUnit 5 test foundation
- [x] Frontend syntax check
- [ ] Expand tests to project target (>=40% service-method coverage)
- [x] Environment-variable based secrets
- [x] Role-aware JWT authorities
- [x] CORS configuration

## CI/CD
- [x] GitHub Actions CI workflow included
- [ ] Add deployment secrets/hosting credentials

## Evidence for Review-II
1. Live frontend URL
2. Live backend URL + `/api/health`
3. Swagger UI URL
4. GitHub Actions successful run
5. JUnit test report
6. ER, Class and Architecture diagrams
7. README v2 and CHANGELOG

> Cloud deployment cannot be completed inside this ZIP because it requires the team's hosting accounts, database credentials and secrets. The project includes deployment-ready configuration and a checklist for those final account-specific steps.
