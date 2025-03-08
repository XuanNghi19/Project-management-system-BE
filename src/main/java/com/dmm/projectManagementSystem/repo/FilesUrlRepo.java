package com.dmm.projectManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dmm.projectManagementSystem.model.FilesUrl;

@Repository
public interface FilesUrlRepo extends JpaRepository<FilesUrl, Long> {}
