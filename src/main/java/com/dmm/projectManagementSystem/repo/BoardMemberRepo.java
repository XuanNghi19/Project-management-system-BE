package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMemberRepo extends JpaRepository<BoardMember, Long> {}
