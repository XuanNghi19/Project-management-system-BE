package com.dmm.projectManagementSystem.service.admin.classTopicManagement;

import com.dmm.projectManagementSystem.dto.classTopic.*;
import com.dmm.projectManagementSystem.dto.studentTopic.StudentTopicResponse;
import com.dmm.projectManagementSystem.enums.ActionTypes;
import com.dmm.projectManagementSystem.model.*;
import com.dmm.projectManagementSystem.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassTopicManagementServiceImpl implements ClassTopicManagementService {

    final private ClassTopicRepo classTopicRepo;
    final private UserRepo userRepo;
    final private TopicSemesterRepo topicSemesterRepo;
    final private MajorRepo majorRepo;

    final private StudentTopicRepo studentTopicRepo;
    final private StudentTopicService studentTopicService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Pair<String, Boolean> addClassTopic(CreateClassTopicRequest request) throws Exception {

        User teacher = userRepo.findByIdNum(request.getTeacherIdNum())
                .orElseThrow(() -> new Exception("Khong tim thay nguoi dung voi idNum: " + request.getTeacherIdNum()));
        TopicSemester exTopicSemester = topicSemesterRepo.findById(request.getTopicSemesterID())
                .orElseThrow(() -> new Exception("Khong tim thay course voi id: " + request.getTopicSemesterID()));
        Major exMajor = majorRepo.findById(request.getMajorID())
                .orElseThrow(() -> new Exception("Khong tim thay major voi id: " + request.getMajorID()));

        // try cho rollback
        try {
            ClassTopic classTopic = classTopicRepo.save(ClassTopic.fromCreateClassTopicRequest(request, teacher, exTopicSemester, exMajor));

            for (var x : request.getStudentTopicList()) {
                studentTopicService.addStudentTopic(classTopic, x);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Pair.of("Add classTopic Successes!", true);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Pair<String, Boolean> updateClassTopic(UpdateClassTopicRequest request) throws Exception {
        ClassTopic exClassTopic = classTopicRepo.findById(request.getId())
                .orElseThrow(() -> new Exception("Khong tim thay classTopic voi id: " + request.getId()));
        User teacher = userRepo.findByIdNum(request.getTeacherIdNum())
                .orElseThrow(() -> new Exception("Khong tim thay nguoi dung voi idNum: " + request.getTeacherIdNum()));
        Major exMajor = majorRepo.findById(request.getMajorID())
                .orElseThrow(() -> new Exception("Khong tim thay major voi id: " + request.getMajorID()));

        try {
            ClassTopic updateClassTopic = classTopicRepo.save(ClassTopic.fromUpdateClassTopicRequest(
                    request,
                    teacher,
                    exClassTopic.getTopicSemester(),
                    exMajor
            ));

            for (var x : request.getStudentTopicList()) {
                if (x.getAction() == ActionTypes.DELETE) {
                    studentTopicService.deleteStudentTopic(x.getId());
                } else if (x.getAction() == ActionTypes.CREATE) {
                    studentTopicService.addStudentTopic(updateClassTopic, x);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Pair.of("Update classTopic Successes!", true);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Pair<String, Boolean> deleteClassTopic(Long classTopicID) throws Exception {

        if (classTopicRepo.existsById(classTopicID)) {
            ClassTopic exClasTopic = classTopicRepo.findById(classTopicID)
                    .orElseThrow(() -> new Exception("Khong tim thay class topic voi id: " + classTopicID));

            try {
                if (studentTopicRepo.existsByClassTopic(exClasTopic)) {
                    studentTopicRepo.deleteAllByClassTopic(exClasTopic);
                }
                classTopicRepo.deleteById(classTopicID);
                return Pair.of("Delete Class Topic Successes!", true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        return Pair.of("Delete Class Topic Fail!", false);
    }

    @Override
    public ClassTopicListByPageResponse getAllClassTopic(String name, Long topicSemesterID, Long majorID, int page, int limit) throws Exception {
        TopicSemester topicSemester = null;
        Major major = null;

        if (topicSemesterID != null) {
            topicSemester = topicSemesterRepo.findById(topicSemesterID)
                    .orElseThrow(() -> new Exception("Khong tim thay topic semester voi id: " + topicSemesterID));
        }
        if (majorID != null) {
            major = majorRepo.findById(majorID)
                    .orElseThrow(() -> new Exception("Khong tim thay major voi id: " + majorID));
        }

        Page<ClassTopicResponse> classTopicResponsePage = classTopicRepo.findAllClassTopic(
                        name,
                        topicSemesterID,
                        majorID,
                        PageRequest.of(page, limit)
                )
                .map(ClassTopicResponse::fromClassTopic);

        return ClassTopicListByPageResponse.fromSplitPage(
                classTopicResponsePage.getContent(),
                classTopicResponsePage.getTotalPages(),
                page,
                limit
        );
    }

    @Override
    public ClassTopicDetailResponse getClassTopicDetail(Long classTopicID) throws Exception {
        ClassTopic exClassTopic = classTopicRepo.findById(classTopicID)
                .orElseThrow(() -> new Exception("Khong tim thay class topic voi id: " + classTopicID));

        List<StudentTopic> test = studentTopicRepo.findAllByClassTopic(classTopicID);

        List<StudentTopicResponse> studentTopicResponseList = studentTopicRepo.findAllByClassTopic(classTopicID).stream().map(
                StudentTopicResponse::fromStudentTopic
        ).toList();

        return ClassTopicDetailResponse.fromClassTopic(exClassTopic, studentTopicResponseList);
    }
}
