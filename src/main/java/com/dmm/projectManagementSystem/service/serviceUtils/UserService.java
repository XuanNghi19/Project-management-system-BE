package com.dmm.projectManagementSystem.service.serviceUtils;

import com.dmm.projectManagementSystem.dto.user.AuthenticationRequest;
import com.dmm.projectManagementSystem.dto.user.AuthenticationResponse;

public interface UserService {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}
