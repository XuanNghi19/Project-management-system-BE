package com.dmm.projectManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TopicUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topicID")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;
}

