package com.photobooth.photoboothapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users") // Menyimpan data ini di koleksi 'users' di MongoDB
public class User {

    @Id
    private String id;

    private String username;
    private String password; // Nanti ini akan kita isi dengan password yang sudah dienkripsi

    // Nanti kita bisa tambahkan field lain seperti email, nama lengkap, dll.
    // Untuk sekarang, ini sudah cukup, sayang.
}