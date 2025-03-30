package com.dmm.projectManagementSystem.service.admin.courseManagement;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.course.CourseListByPageResponse;
import com.dmm.projectManagementSystem.model.Course;
import com.dmm.projectManagementSystem.model.TopicSemester;
import com.dmm.projectManagementSystem.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseManagementServiceImpl implements CourseManagementService {

    final private CourseRepo courseRepo;
    final private UserRepo userRepo;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean addCourse(List<CRUDCourse> cCoursesList) throws Exception {
        try {
            for (var x : cCoursesList) {
                courseRepo.save(Course.fromCRUDCourse(x));
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            throw new Exception(ex);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean updateCourse(CRUDCourse uCourse) throws Exception {
        try {
            courseRepo.save(Course.fromCRUDCourse(uCourse));
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            throw new Exception(ex);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Pair<String, Boolean> deleteCourse(Long id) throws Exception {
        if (courseRepo.existsById(id)) {
            Course exCourse = courseRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Khong tim thay course voi id: " + id));
            if (userRepo.existsByCourse(exCourse)) {
                return Pair.of("still had user exists in the course!", false);
            }
            try {
                courseRepo.deleteById(id);
                return Pair.of("Deleted!", true);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex);
                throw new Exception(ex);
            }
        }
        return Pair.of(String.format("ID doesn't exists: %d", id), false);
    }

    @Override
    public CourseListByPageResponse getAllCourse(String name, LocalDateTime start, LocalDateTime end, int page, int limit) {
        Page<CRUDCourse> crudCourses = courseRepo.findAllCourse(name, start, end, PageRequest.of(page, limit))
                .map(CRUDCourse::fromCourse);

        return CourseListByPageResponse.fromSplitPage(crudCourses.getContent(), crudCourses.getTotalPages(), page, limit);
    }
}
