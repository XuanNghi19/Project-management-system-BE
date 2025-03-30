package com.dmm.projectManagementSystem.service.admin.courseManagement;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.course.CourseListByPageResponse;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseManagementService {
    boolean addCourse(List<CRUDCourse> cCoursesList) throws Exception;
    boolean updateCourse(CRUDCourse uCourse) throws Exception;
    Pair<String, Boolean> deleteCourse(Long id) throws Exception;

    CourseListByPageResponse getAllCourse(String name, LocalDateTime start, LocalDateTime end, int page, int limit);
}
