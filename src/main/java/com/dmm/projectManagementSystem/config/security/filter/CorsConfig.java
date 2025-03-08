package com.dmm.projectManagementSystem.config.security.filter;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {  // Implement WebMvcConfigurer để cấu hình CORS
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Áp dụng CORS cho tất cả các endpoint trong ứng dụng
                .allowedOrigins("*") // Cho phép tất cả các domain (không giới hạn nguồn gốc)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Cho phép các phương thức HTTP này
                .allowedHeaders("Authorization"); // Chỉ cho phép gửi header "Authorization"
    }
}
