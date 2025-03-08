package com.dmm.projectManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "instructor")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacherID")
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "topicID")
    private Topic topic;
}
