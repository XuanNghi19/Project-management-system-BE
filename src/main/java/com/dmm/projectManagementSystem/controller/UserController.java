package com.dmm.projectManagementSystem.controller;

import com.dmm.projectManagementSystem.config.security.JwtUtils;
import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.IntrospectResponse;
import com.dmm.projectManagementSystem.dto.user.AuthenticationRequest;
import com.dmm.projectManagementSystem.dto.user.AuthenticationResponse;
import com.dmm.projectManagementSystem.model.User;
import com.dmm.projectManagementSystem.service.serviceUtils.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/user")
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Operation(summary = "Đăng nhập", description = "Đăng nhập cho cả 3 vai trò")
    @PostMapping("/login")
    public ApiResponse<?> login(
            @RequestBody @Valid AuthenticationRequest request,
            BindingResult result
    ) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ApiResponse.<Boolean>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(errorMessages.toString())
                    .result(false)
                    .build();
        }

        try {
            AuthenticationResponse response = userService.login(request);
            return ApiResponse.builder()
                    .code(HttpStatus.OK.value())
                    .result(response)
                    .message(HttpStatus.OK.toString())
                    .build();
        } catch (Exception ex) {
            return ApiResponse.<String>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.BAD_REQUEST.toString())
                    .result(ex.toString())
                    .build();
        }
    }

    @Operation(summary = "Kiểm tra token", description = "Kiểm tra token là sai, hợp lệ hoặc hết hạn")
    @PostMapping("/introspect")
    public ApiResponse<?> introspect(
             @Param("client token")
             @RequestParam(value = "token", required = true) String token
    ) {
        try {
            IntrospectResponse introspect = jwtUtils.introspect(token);
            if(introspect.getValid()) {
                return ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.toString())
                        .result(introspect)
                        .build();
            } else {
                return ApiResponse.builder()
                        .code(HttpServletResponse.SC_UNAUTHORIZED)
                        .message(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED))
                        .result(introspect)
                        .build();
            }

        } catch (Exception ex) {
            return ApiResponse.<String>builder()
                    .code(HttpServletResponse.SC_UNAUTHORIZED)
                    .message(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED))
                    .result(ex.toString())
                    .build();
        }
    }
}
