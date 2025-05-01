package com.dmm.projectManagementSystem.config.security;


import com.dmm.projectManagementSystem.config.security.filter.JwtFilter;
import com.dmm.projectManagementSystem.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtFilter jwtFilter;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers(
                                    "/api-docs",
                                    "/api-docs/**",
                                    "/swagger-resources",
                                    "/swagger-resources/**",
                                    "/configuration/ui",
                                    "/configuration/security",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/webjars/swagger-ui/**",
                                    "/swagger-ui/index.html")
                            .permitAll()
                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/user/login", apiPrefix),
                                    String.format("%s/user/introspect", apiPrefix)
                            ).permitAll()
                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/user_management/add_teacher", apiPrefix),
                                    String.format("%s/user_management/add_admin", apiPrefix),
                                    String.format("%s/user_management/add_student", apiPrefix),
                                    String.format("%s/department_management/add_department", apiPrefix),
                                    String.format("%s/major_management/add_major", apiPrefix),
                                    String.format("%s/topic_semester_management/add_topic_semester", apiPrefix),
                                    String.format("%s/course_management/add_course", apiPrefix),
                                    String.format("%s/class_topic_management/add_class_topic", apiPrefix),
                                    String.format("%s/council_management/add_council", apiPrefix)
                            ).hasAnyRole(Role.ADMIN.toString())
                            .requestMatchers(
                                    HttpMethod.PUT,
                                    String.format("%s/user_management/update_user", apiPrefix),
                                    String.format("%s/department_management/update_department", apiPrefix),
                                    String.format("%s/major_management/update_major", apiPrefix),
                                    String.format("%s/topic_semester_management/update_topic_semester", apiPrefix),
                                    String.format("%s/course_management/update_course", apiPrefix),
                                    String.format("%s/class_topic_management/update_class_topic", apiPrefix),
                                    String.format("%s/council_management/update_council", apiPrefix)
                            ).hasAnyRole(Role.ADMIN.toString())
                            .requestMatchers(
                                    HttpMethod.PATCH,
                                    String.format("%s/user_management/change_password", apiPrefix),
                                    String.format("%s/user_management/upload_avatar", apiPrefix),
                                    String.format("%s/topic_management/approve_grade", apiPrefix)
                            ).hasAnyRole(Role.ADMIN.toString())
                            .requestMatchers(
                                    HttpMethod.DELETE,
                                    String.format("%s/user_management/delete_user", apiPrefix),
                                    String.format("%s/department_management/delete_department", apiPrefix),
                                    String.format("%s/major_management/delete_major", apiPrefix),
                                    String.format("%s/topic_semester_management/delete_topic_semester", apiPrefix),
                                    String.format("%s/course_management/delete_course", apiPrefix),
                                    String.format("%s/topic_management/delete_topic", apiPrefix),
                                    String.format("%s/class_topic_management/delete_class_topic", apiPrefix),
                                    String.format("%s/council_management/delete_council", apiPrefix)
                            ).hasAnyRole(Role.ADMIN.toString())
                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/user_management/get_all_user", apiPrefix),
                                    String.format("%s/lookup/search_department", apiPrefix),
                                    String.format("%s/lookup/search_major", apiPrefix),
                                    String.format("%s/lookup/search_topic_semester", apiPrefix),
                                    String.format("%s/lookup/search_course", apiPrefix),
                                    String.format("%s/department_management/get_all_department", apiPrefix),
                                    String.format("%s/major_management/get_all_major", apiPrefix),
                                    String.format("%s/topic_semester_management/get_all_topic_semester", apiPrefix),
                                    String.format("%s/course_management/get_all_course", apiPrefix),
                                    String.format("%s/topic_management/get_all_topic", apiPrefix),
                                    String.format("%s/topic_management/get_detail_topic", apiPrefix),
                                    String.format("%s/class_topic_management/get_all_class_topic", apiPrefix),
                                    String.format("%s/class_topic_management/get_detail_class_topic", apiPrefix),
                                    String.format("%s/council_management/get_all_council", apiPrefix),
                                    String.format("%s/council_management/get_council_detail", apiPrefix)
                            ).hasAnyRole(Role.ADMIN.toString());
                });
        return httpSecurity.build();
    }
}