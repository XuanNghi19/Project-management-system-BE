package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.dto.studentTopic.CUDStudentTopicRequest;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_topic")
public class StudentTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "class_topic_id")
    private ClassTopic classTopic;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    boolean status = false;

    static public StudentTopic fromCUDStudentTopic(
            CUDStudentTopicRequest request,
            ClassTopic classTopic,
            User student
    ){
        return StudentTopic.builder()
                .id(request.getId())
                .classTopic(classTopic)
                .student(student)
                .status(request.isStatus())
                .build();
    }
}
