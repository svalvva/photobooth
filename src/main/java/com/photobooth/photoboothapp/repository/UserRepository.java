package com.photobooth.photoboothapp.repository;

import com.photobooth.photoboothapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    // Metode khusus untuk mencari user berdasarkan username
    Optional<User> findByUsername(String username);

}

// Catatan: MongoRepository sudah menyediakan banyak metode CRUD dasar,