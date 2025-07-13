package com.photobooth.photoboothapp.config;

import com.photobooth.photoboothapp.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Kita tidak lagi menyuntikkan UserService di sini, Spring Security akan menemukannya secara otomatis
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Nonaktifkan CSRF untuk kemudahan
            .authorizeHttpRequests(auth -> auth
                // Izinkan semua orang mengakses pintu registrasi, halaman login, dan file-file dasar
                .requestMatchers("/api/auth/**", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                // Untuk semua halaman atau API lain, pengguna harus sudah login
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
            .loginPage("/login.html") // <-- Arahkan ke file HTML kita
            .loginProcessingUrl("/login") // Biarkan Spring yang menangani proses login
            .defaultSuccessUrl("/index.html", true) // Jika login sukses, arahkan ke studio
            .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // URL untuk logout
                .logoutSuccessUrl("/login.html") // Setelah logout, arahkan ke halaman login
                .permitAll()
            );

        return http.build();
    }
}