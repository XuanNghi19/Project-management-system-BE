package com.dmm.projectManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_topic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StudentTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    private boolean status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_topic_id")
    private ClassTopic classTopic;
}
