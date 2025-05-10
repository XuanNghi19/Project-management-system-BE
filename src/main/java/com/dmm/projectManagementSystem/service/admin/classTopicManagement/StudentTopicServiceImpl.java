package com.dmm.projectManagementSystem.service.admin.classTopicManagement;

import com.dmm.projectManagementSystem.dto.studentTopic.CUDStudentTopicRequest;
import com.dmm.projectManagementSystem.dto.studentTopic.StudentTopicResponse;
import com.dmm.projectManagementSystem.model.*;
import com.dmm.projectManagementSystem.repo.StudentTopicRepo;
import com.dmm.projectManagementSystem.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentTopicServiceImpl implements StudentTopicService {

    final private StudentTopicRepo studentTopicRepo;
    final private UserRepo userRepo;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void addStudentTopic(ClassTopic classTopic, CUDStudentTopicRequest requests) throws Exception {
        try {

            /*
             * Tìm danh sách student Topic và so sánh lớp của các student topic đó có kỳ học giống với class topic muốn thêm không
             * nếu tồn tại student topic có class topic trùng với kỳ học của class topic hiện tại thì báo lỗi
             * và không lưu
             */

            User exStudent = userRepo.findByIdNum(requests.getStudentIdNum())
                    .orElseThrow(() -> new Exception("Khong tim thay nguoi dung voi idNum: " + requests.getStudentIdNum()));

            List<StudentTopic> studentTopics = studentTopicRepo.findAllByStudent(exStudent);
            TopicSemester exTopicSemester = classTopic.getTopicSemester();
            for (StudentTopic studentTopic : studentTopics) {
                if(studentTopic.getClassTopic().getTopicSemester().equals(exTopicSemester)) {
                    throw new Exception("Student already exists in this semester's topic class, student code: " + exStudent.getIdNum());
                }
            }
            StudentTopic newStudentTopic = StudentTopic.fromCUDStudentTopic(requests, classTopic, exStudent);
            newStudentTopic.setId(null);
            studentTopicRepo.save(newStudentTopic);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void deleteStudentTopic(String studentIdNum) throws Exception {
        User exStudent = userRepo.findByIdNum(studentIdNum)
                .orElseThrow(() -> new Exception("Khong tim thay nguoi dung voi idNum: " + studentIdNum));
        if (studentTopicRepo.existsByStudent(exStudent)) {
            try {
                studentTopicRepo.deleteByStudent(exStudent);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }
}
