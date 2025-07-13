package com.photobooth.photoboothapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List; // Pastikan baris import ini ada

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "photo_strips")
public class PhotoStrip {

    @Id
    private String id;
    private String userId;

    private String creatorName;
    private String finalStripUrl;
    private List<String> sourceImages; // <- Ini bagian renovasinya
    private Customization customization;   // <- Ini bagian renovasinya
    private LocalDateTime createdAt;
    // Di dalam class PhotoStrip
// ... sisa kode ...
}