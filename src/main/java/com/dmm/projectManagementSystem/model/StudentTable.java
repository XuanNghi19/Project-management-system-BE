package com.dmm.projectManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_table")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StudentTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacherID")
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "studentID")
    private User student;
}
