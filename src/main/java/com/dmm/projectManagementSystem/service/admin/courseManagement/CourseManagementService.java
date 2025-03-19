package com.dmm.projectManagementSystem.service.admin.courseManagement;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.course.CourseListByPageResponse;
import org.springframework.data.util.Pair;

import java.util.List;

public interface CourseManagementService {
    boolean addCourse(List<CRUDCourse> cCoursesList);
    boolean updateCourse(CRUDCourse uCourse);
    Pair<String, Boolean> deleteCourse(Long id);

    CourseListByPageResponse getAllCourse(String name, int page, int limit);
}
