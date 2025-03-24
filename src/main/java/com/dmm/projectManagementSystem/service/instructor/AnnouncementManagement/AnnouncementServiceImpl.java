//package com.dmm.projectManagementSystem.service.instructor.AnnouncementManagement;
//
//import com.dmm.projectManagementSystem.dto.announcement.CreateAnnouncementDTO;
//import com.dmm.projectManagementSystem.model.Announcement;
//import com.dmm.projectManagementSystem.model.Topic;
//import com.dmm.projectManagementSystem.repo.AnnouncementRepo;
//import com.dmm.projectManagementSystem.repo.TopicRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class AnnouncementServiceImpl implements AnnouncementService {
//    @Autowired
//    private AnnouncementRepo announcementRepo;
//    @Autowired
//    private TopicRepo topicRepo;
//    @Override
//    public Announcement createAnnouncement(CreateAnnouncementDTO announcementDTO) {
//        Optional<Topic> topic=topicRepo.findById(announcementDTO.getTopicId());
//        Announcement announcement= Announcement.builder()
//                .title(announcementDTO.getTitle())
//                .content(announcementDTO.getContent())
//                .topic(topic.get())
//                .datePosted(announcementDTO.getDatePosted())
//                .build();
//        return announcementRepo.save(announcement);
//    }
//}
