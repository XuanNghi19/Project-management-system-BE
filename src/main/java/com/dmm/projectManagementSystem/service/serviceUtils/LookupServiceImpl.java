package com.dmm.projectManagementSystem.service.serviceUtils;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
import com.dmm.projectManagementSystem.dto.user.UserResponse;
import com.dmm.projectManagementSystem.enums.Role;
import com.dmm.projectManagementSystem.repo.*;
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
    final private UserRepo userRepo;

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

    @Override
    public List<UserResponse> searchInstructor(String name) {
        return userRepo.findByNameAndRole(name, Role.INSTRUCTORS.name()).stream().map(UserResponse::fromUser).toList();
    }

    @Override
    public List<UserResponse> searchStudent(String name) {
        return  userRepo.findByNameAndRole(name, Role.STUDENT.name()).stream().map(UserResponse::fromUser).toList();
    }

    @Override
    public UserResponse searchSingleStudent(String idNum) throws Exception {
        return userRepo.findByIdNum(idNum).map(UserResponse::fromUser).orElseThrow(() -> new Exception("Mã sinh viên không hợp lệ: " + idNum));
    }
}
