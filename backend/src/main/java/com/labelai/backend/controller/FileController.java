package com.labelai.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final Path uploadDir =
            Paths.get("uploads");

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file) {

        try {

            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "data", Map.of(), "message", "Please select a file"));
            }

            Files.createDirectories(uploadDir);

            String originalName = file.getOriginalFilename() == null ? "dataset.csv" : Paths.get(file.getOriginalFilename()).getFileName().toString();
            String extension = originalName.contains(".") ? originalName.substring(originalName.lastIndexOf('.')).replaceAll("[^.a-zA-Z0-9]", "") : ".data";
            String fileName = UUID.randomUUID() + extension;
            Path filePath = uploadDir.resolve(fileName).normalize();
            if (!filePath.startsWith(uploadDir.toAbsolutePath().normalize()) && !filePath.startsWith(uploadDir.normalize())) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "data", Map.of(), "message", "Invalid file name"));
            }

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return ResponseEntity.ok(
                    Map.of(
                            "message", "File uploaded successfully",
                            "filePath", filePath.toString(),
                            "originalFileName", originalName
                    )
            );

        } catch (IOException e) {

            return ResponseEntity.internalServerError()
                    .body(Map.of("success", false, "data", Map.of(), "message", "File upload failed"));
        }
    }
}