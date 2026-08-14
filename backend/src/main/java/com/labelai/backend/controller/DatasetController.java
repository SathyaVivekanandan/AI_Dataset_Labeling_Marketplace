package com.labelai.backend.controller;

import com.labelai.backend.entity.Dataset;
import com.labelai.backend.entity.User;
import com.labelai.backend.repository.DatasetRepository;
import com.labelai.backend.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/datasets")
@CrossOrigin(origins = "*")
public class DatasetController {

    private final DatasetRepository datasetRepository;
    private final UserRepository userRepository;

    public DatasetController(
            DatasetRepository datasetRepository,
            UserRepository userRepository
    ) {
        this.datasetRepository = datasetRepository;
        this.userRepository = userRepository;
    }

    // Get all datasets
    @GetMapping
    public ResponseEntity<List<Dataset>> getAllDatasets() {
        return ResponseEntity.ok(datasetRepository.findAll());
    }

    // Get dataset by ID
    @GetMapping("/{id}")
    public ResponseEntity<Dataset> getDatasetById(@PathVariable Long id) {
        return datasetRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create dataset
    @PostMapping
    public ResponseEntity<?> createDataset(
            @RequestParam Long userId,
            @RequestBody DatasetRequest request
    ) {

        User owner = userRepository.findById(userId)
                .orElse(null);

        if (owner == null) {
            return ResponseEntity.badRequest()
                    .body("User not found");
        }

        Dataset dataset = new Dataset(
                request.title,
                request.description,
                request.filePath,
                owner
        );

        return ResponseEntity.ok(datasetRepository.save(dataset));
    }

    // Delete dataset
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDataset(@PathVariable Long id) {

        if (!datasetRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        datasetRepository.deleteById(id);

        return ResponseEntity.ok("Dataset deleted successfully");
    }

    // Request body class
    public static class DatasetRequest {

        public String title;
        public String description;
        public String filePath;
    }
}