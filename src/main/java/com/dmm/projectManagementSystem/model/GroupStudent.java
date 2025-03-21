package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.enums.MembershipPosition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_student")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GroupStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    private MembershipPosition position;
}
