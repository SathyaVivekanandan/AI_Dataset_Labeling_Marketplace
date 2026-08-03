# Entity Relationship (ER) Diagram

## Entities

### User
- user_id (PK)
- name
- email
- password
- role
- created_at

### Dataset
- dataset_id (PK)
- title
- description
- file_path
- uploaded_by (FK → User)
- uploaded_at

### Project
- project_id (PK)
- project_name
- dataset_id (FK → Dataset)
- owner_id (FK → User)
- created_at

### Task
- task_id (PK)
- project_id (FK → Project)
- annotator_id (FK → User)
- status
- assigned_at

### Annotation
- annotation_id (PK)
- task_id (FK → Task)
- label
- submitted_at

### Review
- review_id (PK)
- annotation_id (FK → Annotation)
- reviewer_id (FK → User)
- review_status
- remarks
- reviewed_at

---

## Relationships

User (Owner) → Uploads → Dataset

Dataset → Belongs To → Project

Project → Contains → Task

Task → Assigned To → Annotator

Task → Produces → Annotation

Reviewer → Reviews → Annotation