package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.enums.TeamStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
//@Table(name = "project_user")
@Table(name = "team")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;
    @OneToOne
    @JoinColumn(name = "topicID")
    @JsonIgnore
    private Topic topic;

    @Enumerated(EnumType.STRING)
    private ProjectStage status;

    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "topic_semester_id")
    private TopicSemester topicSemester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "major_id")
    private Major major;

    @OneToMany (mappedBy = "team", cascade = CascadeType.ALL)
    @JsonIgnore
    List<TeamMember> listStudent;


}

