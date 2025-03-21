package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Group;
import com.dmm.projectManagementSystem.model.GroupStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupStudentRepo extends JpaRepository<GroupStudent, Long> {
    List<GroupStudent> findAllByGroup(Group group);
}
