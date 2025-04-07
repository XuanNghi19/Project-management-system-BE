package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.dto.boardMember.CRUDBoardMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board_member")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BoardMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "council_id")
    private Council council;

    static public BoardMember fromCRUDBoardMember(
            CRUDBoardMember crudBoardMember,
            Council council,
            User user
    ) {
        return BoardMember.builder()
                .id(crudBoardMember.getId())
                .position(crudBoardMember.getPosition())
                .user(user)
                .council(council)
                .build();
    }
}

