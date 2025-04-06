package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.enums.MembershipPosition;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "team_member")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "student_id")
    private User student;

    @Enumerated(EnumType.STRING)
    private MembershipPosition position;


}

