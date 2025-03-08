package com.dmm.projectManagementSystem.model;

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

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "councilID")
    private Council council;
}

