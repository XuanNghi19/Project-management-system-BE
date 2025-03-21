package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dmm.projectManagementSystem.model.FilesUrl;

import java.util.List;

@Repository
public interface FilesUrlRepo extends JpaRepository<FilesUrl, Long> {
    List<FilesUrl> findAllByTopic(Topic topic);
    boolean deleteAllByTopic(Topic topic);
}
