package com.dmm.projectManagementSystem.service.instructor.topicManagement;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.topic.UpdateTopicDTO;
import com.dmm.projectManagementSystem.exception.InvalidException;
import com.dmm.projectManagementSystem.model.Topic;
import com.dmm.projectManagementSystem.repo.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepo topicRepo;
    @Override
    public List<Topic> getListTopic(Long id) {
        return null;
    }

    @Override
    public ApiResponse<String> approvalTopic(UpdateTopicDTO updateTopicDTO) {
        if(!topicRepo.existsById(updateTopicDTO.getId())) throw new InvalidException("khong ton tai topic");
        Optional<Topic> topic=topicRepo.findById(updateTopicDTO.getId());
        topic.get().setProjectStage(updateTopicDTO.getProjectStage());
        topicRepo.save(topic.get());
        return new ApiResponse<>(1000,"Đã cập nhật thành công",null);
    }
}
