package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.dto.studentTopic.CUDStudentTopicRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinColumn(name = "class_topic_id")
    private ClassTopic classTopic;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private User student;

    private boolean status = false;

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

    @Override
    public String toString() {
        return "StudentTopic{id=" + id + ", status=" + status + "}";
    }
}
