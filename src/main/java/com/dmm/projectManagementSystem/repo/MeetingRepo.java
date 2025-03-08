package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepo extends JpaRepository<Meeting, Long> {}
