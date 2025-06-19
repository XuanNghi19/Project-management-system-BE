package com.dmm.projectManagementSystem.model;
import com.dmm.projectManagementSystem.dto.council.CreateCouncilRequest;
import com.dmm.projectManagementSystem.dto.council.UpdateCouncilRequest;
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

    @Column(name = "file_url", columnDefinition = "TEXT")
    private String fileUrl;

    private String location;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "topic_semester_id")
    private TopicSemester topicSemester;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    static public Council fromCreateCouncilRequest(
            CreateCouncilRequest createCouncilRequest,
            TopicSemester topicSemester,
            Department department
    ) {
        return Council.builder()
                .name(createCouncilRequest.getName())
                .fileUrl(createCouncilRequest.getFileUrl())
                .location(createCouncilRequest.getLocation())
                .startTime(createCouncilRequest.getStartTime())
                .endTime(createCouncilRequest.getEndTime())
                .topicSemester(topicSemester)
                .department(department)
                .build();
    }

    static public Council fromUpdateCouncilRequest(
            UpdateCouncilRequest updateCouncilRequest,
            TopicSemester topicSemester,
            Department department
    ) {
        return Council.builder()
                .id(updateCouncilRequest.getId())
                .name(updateCouncilRequest.getName())
                .fileUrl(updateCouncilRequest.getFileUrl())
                .location(updateCouncilRequest.getLocation())
                .startTime(updateCouncilRequest.getStartTime())
                .endTime(updateCouncilRequest.getEndTime())
                .topicSemester(topicSemester)
                .department(department)
                .build();
    }
}
