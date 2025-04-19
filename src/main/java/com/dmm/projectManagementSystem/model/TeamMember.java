package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.enums.MembershipPosition;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    private MembershipPosition position;
}
