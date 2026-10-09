package com.labelai.backend.controller;

import com.labelai.backend.entity.Project;
import com.labelai.backend.entity.Task;
import com.labelai.backend.entity.User;
import com.labelai.backend.repository.ProjectRepository;
import com.labelai.backend.repository.TaskRepository;
import com.labelai.backend.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskController(
            TaskRepository taskRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository) {

        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<?> createTask(
            @RequestParam Long projectId,
            @RequestParam Long annotatorId) {

        Project project = projectRepository.findById(projectId)
                .orElse(null);

        User annotator = userRepository.findById(annotatorId)
                .orElse(null);

        if (project == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "data", Map.of(), "message", "Project not found"));
        }

        if (annotator == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "data", Map.of(), "message", "Annotator not found"));
        }

        Task task = new Task(project, annotator);

        return ResponseEntity.ok(taskRepository.save(task));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTask(@PathVariable Long id) {

        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        taskRepository.deleteById(id);

        return ResponseEntity.ok(Map.of("message", "Task deleted successfully"));
    }
}