package com.dmm.projectManagementSystem.service.student.studentService;

import com.dmm.projectManagementSystem.config.security.JwtUtils;
import com.dmm.projectManagementSystem.dto.user.UserResponse;
import com.dmm.projectManagementSystem.model.User;
import com.dmm.projectManagementSystem.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final UserRepo userRepo;
    private final JwtUtils jwtUtils;
    public UserResponse handleGetCurrentStudent (String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String idNum = jwtUtils.getIdNumFromToken(token);
        User user = userRepo.findByIdNum(idNum)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với idNum: " + idNum));
        return UserResponse.fromUser(user);

    }

}
