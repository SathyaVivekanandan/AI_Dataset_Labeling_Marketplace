package com.labelai.backend.controller;

import com.labelai.backend.entity.Annotation;
import com.labelai.backend.entity.Task;
import com.labelai.backend.repository.AnnotationRepository;
import com.labelai.backend.repository.TaskRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annotations")
@CrossOrigin(origins = "*")
public class AnnotationController {

    private final AnnotationRepository annotationRepository;
    private final TaskRepository taskRepository;

    public AnnotationController(
            AnnotationRepository annotationRepository,
            TaskRepository taskRepository) {

        this.annotationRepository = annotationRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public ResponseEntity<List<Annotation>> getAllAnnotations() {
        return ResponseEntity.ok(annotationRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Annotation> getAnnotationById(
            @PathVariable Long id) {

        return annotationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createAnnotation(
            @RequestParam Long taskId,
            @RequestBody AnnotationRequest request) {

        Task task = taskRepository.findById(taskId)
                .orElse(null);

        if (task == null) {
            return ResponseEntity.badRequest()
                    .body("Task not found");
        }

        Annotation annotation =
                new Annotation(request.label, task);

        return ResponseEntity.ok(
                annotationRepository.save(annotation)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnnotation(
            @PathVariable Long id) {

        if (!annotationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        annotationRepository.deleteById(id);

        return ResponseEntity.ok(
                "Annotation deleted successfully"
        );
    }

    public static class AnnotationRequest {
        public String label;
    }
}