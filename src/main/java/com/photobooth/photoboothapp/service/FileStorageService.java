package com.photobooth.photoboothapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    // Menunjuk ke folder 'uploads' yang kita buat tadi
    private final Path rootLocation = Paths.get("uploads");

    public FileStorageService() {//ini akan dipanggil saat aplikasi dimulai
        try {
            // Membuat folder 'uploads' jika belum ada saat aplikasi pertama kali berjalan
            Files.createDirectories(rootLocation); 
        } catch (IOException e) { 
            throw new RuntimeException("Tidak bisa membuat direktori penyimpanan", e);
        }
    }

    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Gagal menyimpan file kosong.");
            }
            // Buat nama file yang unik untuk menghindari penimpaan
            String uniqueFilename = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path destinationFile = this.rootLocation.resolve(Paths.get(uniqueFilename))
                    .normalize().toAbsolutePath();

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            // Kembalikan nama uniknya
            return uniqueFilename;

        } catch (IOException e) {
            throw new RuntimeException("Gagal menyimpan file.", e);
        }
    }
}