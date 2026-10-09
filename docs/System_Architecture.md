# System Architecture

The React client sends JSON requests to the Spring Boot REST API and attaches the JWT for authenticated routes. Controllers coordinate requests; Spring Data JPA persists six relational entities in MySQL. File upload stores files on local disk in this prototype. A deployed multi-instance application must replace local disk storage with persistent object storage. The AI suggestion service is a deterministic keyword baseline and requires human review.

![Architecture](diagrams/architecture.png)

Editable source: `diagrams/architecture.dot`.
