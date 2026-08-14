# Entity Relationship (ER) Diagram

```mermaid
erDiagram
    USER {
        BIGINT id PK
        VARCHAR name
        VARCHAR email UK
        VARCHAR password
        VARCHAR role
    }

    DATASET {
        BIGINT id PK
        VARCHAR title
        VARCHAR description
        VARCHAR file_path
        BIGINT owner_id FK
    }

    USER ||--o{ DATASET : owns
```

## Relationship
- One `USER` can own many `DATASET` records.
- Each `DATASET` belongs to one `USER`.
- `USER.id` is the primary key.
- `DATASET.id` is the primary key.
- `DATASET.owner_id` is a foreign key referencing `USER.id`.
