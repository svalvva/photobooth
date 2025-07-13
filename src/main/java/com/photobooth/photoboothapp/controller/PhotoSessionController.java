package com.photobooth.photoboothapp.controller;

import com.photobooth.photoboothapp.model.Customization;
import com.photobooth.photoboothapp.model.PhotoSession;
import com.photobooth.photoboothapp.model.PhotoStrip;
import com.photobooth.photoboothapp.service.FileStorageService;
import com.photobooth.photoboothapp.service.PhotoSessionService;
import com.photobooth.photoboothapp.service.PhotoStripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "http://localhost:8080")
public class PhotoSessionController {

    @Autowired
    private PhotoSessionService sessionService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private PhotoStripService photoStripService;

    @PostMapping("/start")
    public ResponseEntity<PhotoSession> startNewSession() {
        return ResponseEntity.ok(sessionService.createNewSession());
    }

    @PostMapping("/{sessionId}/photos")
    public ResponseEntity<String> addPhotoToSession(@PathVariable String sessionId, @RequestParam("photo") MultipartFile file) {
        String storedPath = fileStorageService.store(file);
        sessionService.addPhotoToSession(sessionId, storedPath);
        return ResponseEntity.ok(storedPath);
    }
    
    @PostMapping("/{sessionId}/finish")
    public ResponseEntity<PhotoStrip> finishSession(
            @PathVariable String sessionId,
            @RequestBody Customization customizationDetails) {
        
        PhotoStrip finalStrip = photoStripService.processAndSaveStrip(sessionId, customizationDetails);
        return ResponseEntity.ok(finalStrip);
    }
}