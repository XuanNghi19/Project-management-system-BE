package com.dmm.projectManagementSystem.model;
import com.dmm.projectManagementSystem.dto.council.CreateCouncilRequest;
import com.dmm.projectManagementSystem.dto.council.UpdateCouncilRequest;
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

    @ManyToOne
    @JoinColumn(name = "courseID")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "departmentID")
    private Department department;

    static public Council fromCreateCouncilRequest(
            CreateCouncilRequest createCouncilRequest,
            Course course,
            Department department
    ) {
        return Council.builder()
                .name(createCouncilRequest.getName())
                .fileUrl(createCouncilRequest.getFileUrl())
                .location(createCouncilRequest.getLocation())
                .startTime(createCouncilRequest.getStartTime())
                .endTime(createCouncilRequest.getEndTime())
                .course(course)
                .department(department)
                .build();
    }

    static public Council fromUpdateCouncilRequest(
            UpdateCouncilRequest updateCouncilRequest,
            Course course,
            Department department
    ) {
        return Council.builder()
                .id(updateCouncilRequest.getId())
                .name(updateCouncilRequest.getName())
                .fileUrl(updateCouncilRequest.getFileUrl())
                .location(updateCouncilRequest.getLocation())
                .startTime(updateCouncilRequest.getStartTime())
                .endTime(updateCouncilRequest.getEndTime())
                .course(course)
                .department(department)
                .build();
    }
}
