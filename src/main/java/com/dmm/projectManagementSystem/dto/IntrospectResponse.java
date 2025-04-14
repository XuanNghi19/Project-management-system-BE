package com.dmm.projectManagementSystem.dto;

import com.dmm.projectManagementSystem.enums.ErrorCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class IntrospectResponse {
    Boolean valid;
    String errorMessage;
    ErrorCode errorCode;
}
