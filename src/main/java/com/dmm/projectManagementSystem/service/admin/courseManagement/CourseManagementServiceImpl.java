package com.dmm.projectManagementSystem.service.admin.courseManagement;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.course.CourseListByPageResponse;
import com.dmm.projectManagementSystem.model.Course;
import com.dmm.projectManagementSystem.repo.CouncilRepo;
import com.dmm.projectManagementSystem.repo.CourseRepo;
import com.dmm.projectManagementSystem.repo.TopicRepo;
import com.dmm.projectManagementSystem.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseManagementServiceImpl implements CourseManagementService{

    final private CourseRepo courseRepo;
    final private UserRepo userRepo;
    final private TopicRepo topicRepo;
    final private CouncilRepo councilRepo;

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
    public Pair<String, Boolean> deleteCourse(Long id) {
        if(courseRepo.existsById(id)) {
            Course exCourse = courseRepo.findById(id)
                            .orElseThrow(() -> new RuntimeException("Khong tim thay course voi id: " + id));
            if(userRepo.existsByCourse(exCourse) || topicRepo.existsByCourse(exCourse) || councilRepo.existsByCourse(exCourse)) {
                return Pair.of("still exists in the course!", false);
            }
            courseRepo.deleteById(id);
            return Pair.of("Deleted!", true);
        }
        return Pair.of(String.format("ID doesn't exists: %d", id), false);
    }

    @Override
    public CourseListByPageResponse getAllCourse(String name, int page, int limit) {
        Page<CRUDCourse> crudCourses = courseRepo.findAllCourse(name, PageRequest.of(page, limit))
                .map(CRUDCourse::fromCourse);

        return com.dmm.projectManagementSystem.dto.course.CourseListByPageResponse.fromSplitPage(crudCourses.getContent(), crudCourses.getTotalPages(), page, limit);
    }
}
