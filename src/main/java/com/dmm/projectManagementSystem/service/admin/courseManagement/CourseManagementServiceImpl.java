package com.dmm.projectManagementSystem.service.admin.courseManagement;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.course.CourseListByPageResponse;
import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.department.DepartmentListByPageResponse;
import com.dmm.projectManagementSystem.model.Course;
import com.dmm.projectManagementSystem.model.Department;
import com.dmm.projectManagementSystem.repo.CourseRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseManagementServiceImpl implements CourseManagementService{

    final private CourseRepo courseRepo;

    @Transactional
    @Override
    public boolean addCourse(List<CRUDCourse> cCoursesList) {
        try {
            for (var x : cCoursesList) {
                courseRepo.save(Course.fromCRUDCourse(x));
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            return false;
        }
    }

    @Transactional
    @Override
    public boolean updateCourse(CRUDCourse uCourse) {
        try {
            courseRepo.save(Course.fromCRUDCourse(uCourse));
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            return false;
        }
    }

    @Transactional
    @Override
    public boolean deleteCourse(Long id) {
        if(courseRepo.existsById(id)) {
            courseRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public CourseListByPageResponse getAllCourse(String name, int page, int limit) {
        Page<CRUDCourse> crudCourses = courseRepo.findAllCourse(name, PageRequest.of(page, limit))
                .map(CRUDCourse::fromCourse);

        return CourseListByPageResponse.fromSplitPage(crudCourses.getContent(), crudCourses.getTotalPages(), page, limit);
    }
}
