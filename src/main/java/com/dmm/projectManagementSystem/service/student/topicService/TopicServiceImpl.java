package com.dmm.projectManagementSystem.service.student.topicService;

import com.dmm.projectManagementSystem.enums.MembershipPosition;
import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.model.Team;
import com.dmm.projectManagementSystem.model.TeamMember;
import com.dmm.projectManagementSystem.model.Topic;
import com.dmm.projectManagementSystem.repo.TeamMemberRepo;
import com.dmm.projectManagementSystem.repo.TeamRepo;
import com.dmm.projectManagementSystem.repo.TopicRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private TeamRepo teamRepo;
    @Autowired
    private TeamMemberRepo teamMemberRepo;
    @Override
    public boolean handleRegisterTopic(Long leaderId, String topicName) {
        try {
            Optional<TeamMember> leaderTeam = this.teamMemberRepo.findByStudentId(leaderId);
            leaderTeam.orElseThrow(() -> new NoSuchElementException("Người dùng chưa tạo nhóm"));
            MembershipPosition positionInTeam = leaderTeam.get().getPosition();
            if (positionInTeam != MembershipPosition.LEADER){
                System.out.println("Không phải là trưởng nhóm nên không đăng ký được đề tài !");
                return false;
            }
          Topic studentTopic = new Topic();
          studentTopic.setName(topicName);
          studentTopic.setProjectStage(ProjectStage.IDEATION);
            Optional<Team> team = this.teamRepo.findById(leaderTeam.get().getTeam().getId());
            team.orElseThrow(() -> new NoSuchElementException("Không tìm thấy nhóm của bạn!"));
            team.get().setTopic(studentTopic);
            this.teamRepo.save(team.get());

            this.topicRepo.save(studentTopic);

            return true;

        }catch (Exception ex){
            System.out.println("Đã có lỗi " + ex.getMessage() + " trong quá trình đăng ký người dùng !");
            return false;
        }
    }
    @Override
    public boolean handleUpdateTopic(Long topicId) {
        Optional<Topic> topicFound = this.topicRepo.findById(topicId);
        if (!topicFound.isPresent()){
            System.out.println("Không tìm thấy đề tài trong CSDL");
            return false;
        }
        return true;
    }


    @Override
    public Topic handleGetTopic(Long id) {
        Optional<Topic> topicFound = this.topicRepo.findById(id);
        if (!topicFound.isPresent()){
            System.out.println("Không tìm thấy đề tài trong CSDL");
            return null;
        }
        return topicFound.get();
    }
}
