package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardMemberRepo extends JpaRepository<BoardMember, Long> {
    List<BoardMember> findByUserId(Long id);
}
