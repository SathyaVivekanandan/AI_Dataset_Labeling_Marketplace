package com.labelai.backend.controller;

import com.labelai.backend.entity.Dataset;
import com.labelai.backend.entity.Project;
import com.labelai.backend.entity.User;
import com.labelai.backend.repository.DatasetRepository;
import com.labelai.backend.repository.ProjectRepository;
import com.labelai.backend.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final DatasetRepository datasetRepository;
    private final UserRepository userRepository;

    public ProjectController(
            ProjectRepository projectRepository,
            DatasetRepository datasetRepository,
            UserRepository userRepository) {

        this.projectRepository = projectRepository;
        this.datasetRepository = datasetRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return projectRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProject(
            @RequestParam Long datasetId,
            @RequestParam Long ownerId,
            @RequestBody ProjectRequest request) {

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElse(null);

        User owner = userRepository.findById(ownerId)
                .orElse(null);

        if (dataset == null) {
            return ResponseEntity.badRequest()
                    .body("Dataset not found");
        }

        if (owner == null) {
            return ResponseEntity.badRequest()
                    .body("Owner not found");
        }

        Project project = new Project(
                request.projectName,
                dataset,
                owner
        );

        return ResponseEntity.ok(projectRepository.save(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {

        if (!projectRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        projectRepository.deleteById(id);

        return ResponseEntity.ok("Project deleted successfully");
    }

    public static class ProjectRequest {
        public String projectName;
    }
}
