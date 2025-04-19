package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.enums.TeamStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "team")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;
    @OneToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private User teacher;


    @Enumerated(EnumType.STRING)
    private TeamStatus status;

    @OneToMany (mappedBy = "team", cascade = CascadeType.ALL)
    List<TeamMember> listStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "topic_semester_id")
    private TopicSemester topicSemester;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;
}

