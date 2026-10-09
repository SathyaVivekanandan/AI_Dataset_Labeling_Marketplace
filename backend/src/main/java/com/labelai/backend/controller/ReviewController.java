package com.labelai.backend.controller;

import com.labelai.backend.entity.Annotation;
import com.labelai.backend.entity.Review;
import com.labelai.backend.entity.User;
import com.labelai.backend.repository.AnnotationRepository;
import com.labelai.backend.repository.ReviewRepository;
import com.labelai.backend.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final AnnotationRepository annotationRepository;
    private final UserRepository userRepository;

    public ReviewController(
            ReviewRepository reviewRepository,
            AnnotationRepository annotationRepository,
            UserRepository userRepository) {

        this.reviewRepository = reviewRepository;
        this.annotationRepository = annotationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(
            @PathVariable Long id) {

        return reviewRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('REVIEWER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<?> createReview(
            @RequestParam Long annotationId,
            @RequestParam Long reviewerId,
            @RequestBody ReviewRequest request) {

        Annotation annotation =
                annotationRepository.findById(annotationId)
                        .orElse(null);

        User reviewer =
                userRepository.findById(reviewerId)
                        .orElse(null);

        if (annotation == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "data", Map.of(), "message", "Annotation not found"));
        }

        if (reviewer == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "data", Map.of(), "message", "Reviewer not found"));
        }

        Review review = new Review(
                request.reviewStatus,
                request.remarks,
                annotation,
                reviewer
        );

        return ResponseEntity.ok(
                reviewRepository.save(review)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteReview(
            @PathVariable Long id) {

        if (!reviewRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        reviewRepository.deleteById(id);

        return ResponseEntity.ok(Map.of("message", "Review deleted successfully"));
    }

    public static class ReviewRequest {
        public String reviewStatus;
        public String remarks;
    }
}