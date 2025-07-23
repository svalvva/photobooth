package com.photobooth.photoboothapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    // 2. Menentukan Aturan Keamanan untuk Setiap "Pintu"
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Kita nonaktifkan CSRF untuk sementara agar lebih mudah
            .authorizeHttpRequests(auth -> auth
                // Izinkan semua orang (tanpa login) untuk mengakses halaman utama & galeri
                .requestMatchers("/", "/index.html", "/images/**", "/api/photostrips/**").permitAll()
                // Izinkan semua orang untuk mencoba memulai sesi foto
                .requestMatchers("/api/sessions/**").permitAll() 
                // Semua permintaan lain harus sudah login (terotentikasi)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // Gunakan halaman login bawaan dari Spring Security
                .loginPage("/login").permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}