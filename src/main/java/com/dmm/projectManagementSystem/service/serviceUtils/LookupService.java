package com.dmm.projectManagementSystem.service.serviceUtils;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;

import java.util.List;

public interface LookupService {
    List<CRUDDepartment> searchDepartment(String name);
    List<CRUDMajor> searchMajor(String name);
    List<CRUDTopicSemester> searchTopicSemester(String name);
    List<CRUDCourse> searchCourse(String name);
}
