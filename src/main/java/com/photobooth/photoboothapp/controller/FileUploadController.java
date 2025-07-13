package com.photobooth.photoboothapp.controller;

import com.photobooth.photoboothapp.service.FileStorageService; // Import service kita
import org.springframework.beans.factory.annotation.Autowired; // Import Autowired
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:8080")
public class FileUploadController {

    // "Suntikkan" atau panggil asisten kita
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("photo") MultipartFile file) {
        // Suruh asisten untuk menyimpan file
        String filename = fileStorageService.store(file);

        // Buat path yang bisa diakses dari web
        String fileUrl = "/uploads/" + filename;

        // Kembalikan URL file sebagai jawaban
        return ResponseEntity.ok(fileUrl);
    }
}