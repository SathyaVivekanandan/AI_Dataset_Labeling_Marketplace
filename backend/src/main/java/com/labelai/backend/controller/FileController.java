package com.labelai.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class FileController {

    private final Path uploadDir =
            Paths.get("uploads");

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file) {

        try {

            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of(
                                "error",
                                "Please select a file"
                        ));
            }

            Files.createDirectories(uploadDir);

            String fileName =
                    Paths.get(file.getOriginalFilename())
                            .getFileName()
                            .toString();

            Path filePath =
                    uploadDir.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return ResponseEntity.ok(
                    Map.of(
                            "message",
                            "File uploaded successfully",
                            "filePath",
                            filePath.toString()
                    )
            );

        } catch (IOException e) {

            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "error",
                            "File upload failed: "
                                    + e.getMessage()
                    ));
        }
    }
}