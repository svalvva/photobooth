// File: src/main/java/com/photobooth/photoboothapp/service/UserService.java
package com.photobooth.photoboothapp.service;

import com.photobooth.photoboothapp.model.User;
import com.photobooth.photoboothapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Menggunakan "Constructor Injection" yang modern dan rapi
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository; // Ini untuk mengakses data pengguna dari database
        this.passwordEncoder = passwordEncoder; // enkripsi password sebelum disimpan
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Pengguna tidak ditemukan dengan username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    // --- METODE BARU UNTUK REGISTRASI ---
    public User registerNewUser(User user) {
        // Cek dulu apakah username sudah ada yang pakai
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username '" + user.getUsername() + "' sudah terpakai, sayang. Coba yang lain ya.");
        }
        // "Acak" atau enkripsi password sebelum disimpan ke database
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}