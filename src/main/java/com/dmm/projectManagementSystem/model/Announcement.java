package com.dmm.projectManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "announcement")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String datePosted;

    @ManyToOne
    @JoinColumn(name = "topicID")
    private Topic topic;
    @ManyToOne
    @JoinColumn(name="teacher_id")
    private User teacher;
}

