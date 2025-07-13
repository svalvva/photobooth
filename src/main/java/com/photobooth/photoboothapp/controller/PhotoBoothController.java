package com.photobooth.photoboothapp.controller;

import com.photobooth.photoboothapp.model.PhotoStrip;
import com.photobooth.photoboothapp.service.PhotoStripService;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/photostrips")
public class PhotoBoothController {

    @Autowired
    private PhotoStripService photoStripService;

    @GetMapping
    public List<PhotoStrip> getAllPhotoStrips() {
        return photoStripService.getAllPhotoStrips();
    }
}