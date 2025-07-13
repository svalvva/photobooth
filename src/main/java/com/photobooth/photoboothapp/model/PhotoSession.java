// File: src/main/java/com/photobooth/photoboothapp/model/PhotoSession.java

package com.photobooth.photoboothapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PhotoSession {

    private final String sessionId;
    private List<String> photoPaths; 
    // private boolean isComplete; // Biarkan saja warning ini ya, nanti akan kita pakai

    public PhotoSession() {
        this.sessionId = UUID.randomUUID().toString();
        this.photoPaths = new ArrayList<>();
        // this.isComplete = false;
    }

    // Getter untuk sessionId
    public String getSessionId() {
        return sessionId;
    }

    // Getter untuk daftar path foto
    public List<String> getPhotoPaths() {
        return photoPaths;
    }

    // Metode untuk menambahkan satu path foto ke dalam daftar ini
    public void addPhotoPath(String path) {
        this.photoPaths.add(path);
    }
}