package com.dmm.projectManagementSystem.dto.boardMember;

import com.dmm.projectManagementSystem.dto.user.UserResponse;
import com.dmm.projectManagementSystem.enums.ActionTypes;
import com.dmm.projectManagementSystem.model.BoardMember;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CRUDBoardMember {
    Long id;
    String position;
    UserResponse user;

    ActionTypes action;

    static public CRUDBoardMember fromBoardMember(
            BoardMember boardMember
    ) {
        return CRUDBoardMember.builder()
                .id(boardMember.getId())
                .position(boardMember.getPosition())
                .user(UserResponse.fromUser(boardMember.getUser()))
                .build();
    }
}
