package com.photobooth.photoboothapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mendaftarkan folder 'uploads'
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
        
        // TAMBAHKAN INI: Mendaftarkan folder 'images' dari classpath
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
    }
}