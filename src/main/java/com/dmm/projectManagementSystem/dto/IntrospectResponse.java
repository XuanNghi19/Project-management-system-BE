package com.dmm.projectManagementSystem.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class IntrospectResponse {
    Boolean valid;
}
