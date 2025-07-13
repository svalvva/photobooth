package com.photobooth.photoboothapp.repository;

import com.photobooth.photoboothapp.model.PhotoStrip;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoStripRepository extends MongoRepository<PhotoStrip, String> {
    
    // Keajaiban terjadi di sini. Biarkan kosong!

}

//CRUD 