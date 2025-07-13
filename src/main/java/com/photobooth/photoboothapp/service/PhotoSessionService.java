// File: src/main/java/com/photobooth/photoboothapp/service/PhotoSessionService.java

package com.photobooth.photoboothapp.service;

import com.photobooth.photoboothapp.model.PhotoSession;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PhotoSessionService {

    private final Map<String, PhotoSession> activeSessions = new ConcurrentHashMap<>();

    public PhotoSession createNewSession() {
        PhotoSession newSession = new PhotoSession();
        activeSessions.put(newSession.getSessionId(), newSession);
        System.out.println("[SERVICE] Sesi baru dibuat dengan ID: " + newSession.getSessionId());
        return newSession;
    }

    public Optional<PhotoSession> findSessionById(String sessionId) {
        return Optional.ofNullable(activeSessions.get(sessionId));
    }

    // INI METODE YANG TADI SALAH TEMPAT, SEKARANG SUDAH DI RUMAHNYA
    public void addPhotoToSession(String sessionId, String photoPath) {
        findSessionById(sessionId).ifPresent(session -> {
            session.addPhotoPath(photoPath);
            System.out.println("[SERVICE] Foto " + photoPath + " telah ditambahkan ke sesi " + sessionId);
        });
    }
}