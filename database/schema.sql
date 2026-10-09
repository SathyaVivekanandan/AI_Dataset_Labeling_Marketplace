CREATE DATABASE IF NOT EXISTS labelai CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE labelai;
-- Tables are managed by Spring Data JPA (spring.jpa.hibernate.ddl-auto=update).
-- This file documents the expected logical schema; let the application create/upgrade tables.
CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, email VARCHAR(100) NOT NULL UNIQUE, password VARCHAR(255) NOT NULL, role VARCHAR(30) NOT NULL);
CREATE TABLE IF NOT EXISTS datasets (id BIGINT PRIMARY KEY AUTO_INCREMENT, title VARCHAR(150) NOT NULL, description VARCHAR(500), file_path VARCHAR(500), owner_id BIGINT NOT NULL, CONSTRAINT fk_dataset_owner FOREIGN KEY (owner_id) REFERENCES users(id));
CREATE TABLE IF NOT EXISTS projects (id BIGINT PRIMARY KEY AUTO_INCREMENT, project_name VARCHAR(150) NOT NULL, status VARCHAR(50), dataset_id BIGINT NOT NULL, owner_id BIGINT NOT NULL, CONSTRAINT fk_project_dataset FOREIGN KEY (dataset_id) REFERENCES datasets(id), CONSTRAINT fk_project_owner FOREIGN KEY (owner_id) REFERENCES users(id));
CREATE TABLE IF NOT EXISTS tasks (id BIGINT PRIMARY KEY AUTO_INCREMENT, status VARCHAR(50), project_id BIGINT NOT NULL, annotator_id BIGINT NOT NULL, CONSTRAINT fk_task_project FOREIGN KEY (project_id) REFERENCES projects(id), CONSTRAINT fk_task_annotator FOREIGN KEY (annotator_id) REFERENCES users(id));
CREATE TABLE IF NOT EXISTS annotations (id BIGINT PRIMARY KEY AUTO_INCREMENT, label VARCHAR(255) NOT NULL, task_id BIGINT NOT NULL, CONSTRAINT fk_annotation_task FOREIGN KEY (task_id) REFERENCES tasks(id));
CREATE TABLE IF NOT EXISTS reviews (id BIGINT PRIMARY KEY AUTO_INCREMENT, review_status VARCHAR(50), remarks VARCHAR(500), annotation_id BIGINT NOT NULL, reviewer_id BIGINT NOT NULL, CONSTRAINT fk_review_annotation FOREIGN KEY (annotation_id) REFERENCES annotations(id), CONSTRAINT fk_review_reviewer FOREIGN KEY (reviewer_id) REFERENCES users(id));
