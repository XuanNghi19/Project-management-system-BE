package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByIdNum(String idNum);
}
