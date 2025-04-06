package com.dmm.projectManagementSystem.dto.group.res;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AcceptInvitationResDTO {

    private String groupName;

    private boolean status;

    List<UserTeamResDTO> listMember;

}
