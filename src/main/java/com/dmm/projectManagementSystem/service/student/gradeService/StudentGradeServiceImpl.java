package com.dmm.projectManagementSystem.service.student.gradeService;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.grade.StudentGradeResponse;
import com.dmm.projectManagementSystem.model.Grade;
import com.dmm.projectManagementSystem.model.Team;
import com.dmm.projectManagementSystem.model.TeamMember;
import com.dmm.projectManagementSystem.model.Topic;
import com.dmm.projectManagementSystem.repo.TeamMemberRepo;
import com.dmm.projectManagementSystem.repo.TopicRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StudentGradeServiceImpl implements StudentGradeService {

    private final TeamMemberRepo teamMemberRepo;
    private final TopicRepo topicRepo;

    @Override
    public ApiResponseStudent<StudentGradeResponse> getGradeByStudentId(Long studentId) {
        ApiResponseStudent<StudentGradeResponse> response = new ApiResponseStudent<>();

        try {
            // Tìm team member của student
            TeamMember teamMember = teamMemberRepo.findFirstByStudentId(studentId)
                    .orElseThrow(() -> new NoSuchElementException("Sinh viên chưa tham gia nhóm nào"));

            Team team = teamMember.getTeam();

            // Tìm topic của team
            Topic topic = team.getTopic();
            if (topic == null) {
                response.setMessage("Sinh viên chưa được phân công đề tài");
                return response;
            }

            // Lấy grade của topic
            Grade grade = topic.getGrade();

            // Tạo response
            StudentGradeResponse gradeResponse = StudentGradeResponse.fromTopicAndGrade(topic, grade);

            response.setData(gradeResponse);
            response.setMessage("Lấy điểm thành công");

        } catch (NoSuchElementException e) {
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setMessage("Có lỗi xảy ra: " + e.getMessage());
        }

        return response;
    }
}