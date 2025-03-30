package com.dmm.projectManagementSystem.service.serviceUtils;

import com.dmm.projectManagementSystem.config.security.JwtUtils;
import com.dmm.projectManagementSystem.dto.user.AuthenticationRequest;
import com.dmm.projectManagementSystem.dto.user.AuthenticationResponse;
import com.dmm.projectManagementSystem.model.User;
import com.dmm.projectManagementSystem.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    final private UserRepo userRepo;
    final private PasswordEncoder passwordEncoder;
    final private JwtUtils jwtUtils;


    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        String token = "";
        User exUser = userRepo.findByIdNum(authenticationRequest.getIdNum())
                .orElseThrow(() -> new RuntimeException("Khong tim thay nguoi dung voi idNum: " + authenticationRequest.getIdNum()));
        if(passwordEncoder.matches(authenticationRequest.getPassword(), exUser.getPassword()) && exUser.isActive()) {
            token = jwtUtils.generateToken(exUser);

            return AuthenticationResponse.fromAuthenticationRequest(token, true);
        }
        return AuthenticationResponse.fromAuthenticationRequest(token, false);
    }
}
