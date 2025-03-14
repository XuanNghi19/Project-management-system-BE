package com.dmm.projectManagementSystem.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AuthenticationResponse {
    String token;
    Boolean authenticated;

    static public AuthenticationResponse fromAuthenticationRequest(String token, boolean authenticated) {
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(authenticated)
                .build();
    }
}