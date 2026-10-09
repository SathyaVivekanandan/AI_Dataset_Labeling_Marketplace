# Database Design — Current JPA Model

Database name: `labelai`. IDs are `BIGINT` auto-increment primary keys. The authoritative field definitions are the JPA entity classes in `backend/src/main/java/com/labelai/backend/entity/`.

| Table | Important fields | Relationships |
|---|---|---|
| `users` | `id`, `name`, `email` (unique), `password` (BCrypt hash), `role` | Owner, annotator, and reviewer relationships |
| `datasets` | `id`, `title`, `description`, `file_path`, `owner_id` | `owner_id` → `users.id` |
| `projects` | `id`, `project_name`, `status`, `dataset_id`, `owner_id` | Dataset and owner foreign keys |
| `tasks` | `id`, `status`, `project_id`, `annotator_id` | Project and annotator foreign keys |
| `annotations` | `id`, `label`, `task_id` | Task foreign key |
| `reviews` | `id`, `review_status`, `remarks`, `annotation_id`, `reviewer_id` | Annotation and reviewer foreign keys |

Run `database/schema.sql` to create the database and documented tables if you want to provision them manually. Otherwise create the database and let Hibernate `ddl-auto=update` create/update the tables. Do not run a conflicting older schema with different column names/types.
