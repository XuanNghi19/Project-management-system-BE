package com.dmm.projectManagementSystem.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "council")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Council {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "courseID")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "departmentID")
    private Department department;
}
