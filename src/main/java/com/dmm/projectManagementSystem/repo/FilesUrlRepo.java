package com.dmm.projectManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dmm.projectManagementSystem.model.FilesUrl;

import java.util.Optional;

@Repository
public interface FilesUrlRepo extends JpaRepository<FilesUrl, Long> {
    Optional<FilesUrl> findByTopicId(Long topicId);
}
