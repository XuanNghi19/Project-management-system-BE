package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.enums.MembershipPosition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team_member")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @Enumerated(EnumType.STRING)
    private MembershipPosition position;
}
