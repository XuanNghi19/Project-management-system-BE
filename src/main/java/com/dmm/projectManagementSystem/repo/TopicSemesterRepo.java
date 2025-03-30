package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.TopicSemester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TopicSemesterRepo extends JpaRepository<TopicSemester, Long> {
    @Query("""
                select t from TopicSemester t where
                (:name is null or lower(t.name) like lower(concat( '%', :name, '%')))
                    and (
                        (:start is null and :end is null)
                        or (:start is not null and :end is not null and t.startTime >= :start and t.endTime <= :end)
                        or (:start is not null and :end is null and t.startTime >= :start)
                        or (:start is null and :end is not null and t.endTime <= :end)
                    )
                order by t.id desc
            """)
    Page<TopicSemester> findAllTopicSemester(
            @Param("name") String name,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    @Query("""
                select t from TopicSemester t where (:name is null or lower(t.name) like lower(concat( '%', :name, '%')))
                order by t.id desc
            """)
    List<TopicSemester> findAllTopicByName(@Param("name") String name);
}
