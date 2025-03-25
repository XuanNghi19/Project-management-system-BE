package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.TopicSemester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicSemesterRepo extends JpaRepository<TopicSemester, Long> {
    @Query("""
        select t from TopicSemester t where (:name is null or lower(t.name) like lower(concat( '%', :name, '%')))
        order by t.id desc
    """)
    Page<TopicSemester> findAllTopicSemester(@Param("name") String name, Pageable pageable);
}
