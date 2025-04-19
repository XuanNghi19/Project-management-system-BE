package com.dmm.projectManagementSystem.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "topic_semester_id")
    private TopicSemester topicSemester;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
