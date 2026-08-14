# Entity Relationship Diagram

```mermaid
erDiagram

    USER {
        BIGINT id PK
        VARCHAR name
        VARCHAR email UK
        VARCHAR password
        ENUM role
    }

    DATASET {
        BIGINT id PK
        VARCHAR title
        VARCHAR description
        VARCHAR file_path
        BIGINT owner_id FK
    }

    PROJECT {
        BIGINT id PK
        VARCHAR project_name
        VARCHAR status
        BIGINT dataset_id FK
        BIGINT owner_id FK
    }

    TASK {
        BIGINT id PK
        BIGINT project_id FK
        BIGINT annotator_id FK
        VARCHAR status
    }

    ANNOTATION {
        BIGINT id PK
        BIGINT task_id FK
        VARCHAR label
        VARCHAR status
    }

    REVIEW {
        BIGINT id PK
        BIGINT annotation_id FK
        BIGINT reviewer_id FK
        VARCHAR review_status
        VARCHAR remarks
    }

    USER ||--o{ DATASET : owns
    USER ||--o{ PROJECT : creates
    DATASET ||--o{ PROJECT : contains
    PROJECT ||--o{ TASK : contains
    USER ||--o{ TASK : assigned
    TASK ||--o{ ANNOTATION : has
    ANNOTATION ||--o{ REVIEW : receives
    USER ||--o{ REVIEW : performs