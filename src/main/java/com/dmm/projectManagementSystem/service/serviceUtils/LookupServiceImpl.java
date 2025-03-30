package com.dmm.projectManagementSystem.service.serviceUtils;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
import com.dmm.projectManagementSystem.repo.CourseRepo;
import com.dmm.projectManagementSystem.repo.DepartmentRepo;
import com.dmm.projectManagementSystem.repo.MajorRepo;
import com.dmm.projectManagementSystem.repo.TopicSemesterRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LookupServiceImpl implements LookupService {

    final private DepartmentRepo departmentRepo;
    final private MajorRepo majorRepo;
    final private TopicSemesterRepo topicSemesterRepo;
    final private CourseRepo courseRepo;

    @Override
    public List<CRUDDepartment> searchDepartment(String name) {
        return departmentRepo.findAllDepartmentByName(name).stream().map(CRUDDepartment::fromDepartment).toList();
    }

    @Override
    public List<CRUDMajor> searchMajor(String name) {
        return majorRepo.findAllMajorByName(name).stream().map(CRUDMajor::fromMajor).toList();
    }

    @Override
    public List<CRUDTopicSemester> searchTopicSemester(String name) {
        return topicSemesterRepo.findAllTopicByName(name).stream().map(CRUDTopicSemester::fromTopicSemester).toList();
    }

    @Override
    public List<CRUDCourse> searchCourse(String name) {
        return courseRepo.findAllCourseByName(name).stream().map(CRUDCourse::fromCourse).toList();
    }
}
