package com.dmm.projectManagementSystem.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserListByPageResponse {
    List<UserResponse> list;

    int totalPage;
    int crrPage;
    int limit;

    static public UserListByPageResponse fromSplitPage(
            List<UserResponse> list,
            int totalPage,
            int crrPage,
            int limit
    ) {
        return UserListByPageResponse.builder()
                .list(list)
                .totalPage(totalPage)
                .crrPage(crrPage)
                .limit(limit)
                .build();
    }
}
