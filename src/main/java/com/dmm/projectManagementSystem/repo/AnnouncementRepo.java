package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement, Long> {}
