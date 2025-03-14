package com.dmm.projectManagementSystem.config.security;

import com.dmm.projectManagementSystem.model.User;
import com.dmm.projectManagementSystem.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepo userRepo; // Repository để truy vấn dữ liệu người dùng từ DB

    /**
     * Bean mã hóa mật khẩu
     * Sử dụng BCrypt để mã hóa và kiểm tra mật khẩu khi đăng nhập
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Cấu hình UserDetailsService để lấy thông tin user từ database dựa vào idNum (mã số sinh viên/giáo viên)
     * Nếu không tìm thấy user, ném ra lỗi UsernameNotFoundException
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return idNum -> {
            try {
                return userRepo.findByIdNum(idNum)
                        .orElseThrow(() -> new RuntimeException("Khong tim thay user voi idNum: " + idNum)); // Truy vấn user từ database theo idNum
            } catch (RuntimeException e) {
                throw new UsernameNotFoundException("Không tìm thấy sinh viên hoặc giáo viên!");
            }
        };
    }

    /**
     * AuthenticationProvider chịu trách nhiệm xác thực user
     * Sử dụng DaoAuthenticationProvider kết hợp với UserDetailsService và PasswordEncoder
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService()); // Cung cấp UserDetailsService
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // Thiết lập PasswordEncoder
        return daoAuthenticationProvider;
    }

    /**
     * AuthenticationManager chịu trách nhiệm xử lý xác thực trong hệ thống
     * Lấy AuthenticationManager từ cấu hình Spring Security
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
