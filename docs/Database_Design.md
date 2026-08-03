# Database Design

## Database Name
ai_dataset_labeling_marketplace

---

## Table 1: users

| Column Name | Data Type | Constraints |
|-------------|----------|-------------|
| user_id | INT | Primary Key, Auto Increment |
| name | VARCHAR(100) | NOT NULL |
| email | VARCHAR(100) | UNIQUE |
| password | VARCHAR(255) | NOT NULL |
| role | ENUM('ADMIN','OWNER','ANNOTATOR','REVIEWER') | NOT NULL |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |

---

## Table 2: datasets

| Column Name | Data Type | Constraints |
|-------------|----------|-------------|
| dataset_id | INT | Primary Key, Auto Increment |
| title | VARCHAR(150) | NOT NULL |
| description | TEXT | |
| file_path | VARCHAR(255) | NOT NULL |
| uploaded_by | INT | Foreign Key (users.user_id) |
| uploaded_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |

---

## Table 3: projects

| Column Name | Data Type | Constraints |
|-------------|----------|-------------|
| project_id | INT | Primary Key, Auto Increment |
| project_name | VARCHAR(150) | NOT NULL |
| dataset_id | INT | Foreign Key (datasets.dataset_id) |
| owner_id | INT | Foreign Key (users.user_id) |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |

---

## Table 4: tasks

| Column Name | Data Type | Constraints |
|-------------|----------|-------------|
| task_id | INT | Primary Key, Auto Increment |
| project_id | INT | Foreign Key (projects.project_id) |
| annotator_id | INT | Foreign Key (users.user_id) |
| status | VARCHAR(30) | DEFAULT 'Pending' |
| assigned_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |

---

## Table 5: annotations

| Column Name | Data Type | Constraints |
|-------------|----------|-------------|
| annotation_id | INT | Primary Key, Auto Increment |
| task_id | INT | Foreign Key (tasks.task_id) |
| label | TEXT | NOT NULL |
| submitted_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |

---

## Table 6: reviews

| Column Name | Data Type | Constraints |
|-------------|----------|-------------|
| review_id | INT | Primary Key, Auto Increment |
| annotation_id | INT | Foreign Key (annotations.annotation_id) |
| reviewer_id | INT | Foreign Key (users.user_id) |
| review_status | VARCHAR(30) | Approved / Rejected |
| remarks | TEXT | |
| reviewed_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |

---

## Relationships

- One User can upload many Datasets.
- One Dataset belongs to one Project.
- One Project contains many Tasks.
- One Task is assigned to one Annotator.
- One Task produces one Annotation.
- One Annotation is reviewed by one Reviewer.