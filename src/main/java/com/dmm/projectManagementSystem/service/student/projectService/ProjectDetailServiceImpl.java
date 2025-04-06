package com.dmm.projectManagementSystem.service.student.projectService;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.Metadata;
import com.dmm.projectManagementSystem.dto.RestResponse;
import com.dmm.projectManagementSystem.dto.project.CouncilResDTO;
import com.dmm.projectManagementSystem.dto.project.DefenseScheduleResDTO;
import com.dmm.projectManagementSystem.model.*;
import com.dmm.projectManagementSystem.repo.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProjectDetailServiceImpl {
  @Autowired
  private TopicRepo topicRepo;
  @Autowired
  private DefenseScheduleRepo defenseScheduleRepo;
  @Autowired
  private MeetingRepo meetingRepo;
  @Autowired
  private CouncilRepo councilRepo;
  @Autowired
  private EvaluationRepo evaluationRepo;

  public ApiResponseStudent<DefenseScheduleResDTO> handleGetDefenseSchedule (Long topicId) {
    Optional<DefenseSchedule> defenseScheduleDB = this.defenseScheduleRepo.findByTopicId(topicId);
    defenseScheduleDB.orElseThrow(() -> new NoSuchElementException("Không tìm thấy topic trong DB !"));
    DefenseScheduleResDTO defenseScheduleResDTO = new DefenseScheduleResDTO();
    ApiResponseStudent<DefenseScheduleResDTO> apiResponseGetDefence  = new ApiResponseStudent<>();
    apiResponseGetDefence.setData(defenseScheduleResDTO.convertToDefenseScheduleDTO(defenseScheduleDB.get()));
    apiResponseGetDefence.setMessage("Lấy lịch bảo về đồ án thành công !");
    return apiResponseGetDefence;
  }

  public ApiResponseStudent<CouncilResDTO> handleGetCouncil (Long topicId) {
    Optional<DefenseSchedule> defenseScheduleDB = this.defenseScheduleRepo.findByTopicId(topicId);
    defenseScheduleDB.orElseThrow(() -> new NoSuchElementException("Không tìm thấy topic trong DB !"));
    Long councilId = defenseScheduleDB.get().getCouncil().getId();
    Optional<Council> councilDB = this.councilRepo.findById(councilId);
    councilDB.orElseThrow(() ->new NoSuchElementException("Không tìm thấy hội đồng trong csdl"));
    Council council = councilDB.get();
    ApiResponseStudent<CouncilResDTO> apiResponseGetCouncil  = new ApiResponseStudent<>();
    apiResponseGetCouncil.setData(CouncilResDTO.builder()
            .name(council.getName())
            .fileUrl(council.getFileUrl())
            .location(council.getLocation())
            .startTime(council.getStartTime())
            .endTime(council.getEndTime())
            .build());
    apiResponseGetCouncil.setMessage("Lấy hội đồng tham gia đánh giá đồ án thành công");
    return apiResponseGetCouncil;
  }

  public RestResponse<List<Meeting>> handleGetMeeting (Long topicId, int page, int size, String sort) {

    Optional<Topic> topicDB = this.topicRepo.findById(topicId);
    topicDB.orElseThrow(() -> new NoSuchElementException("Không tìm thấy topic trong DB !"));

    Sort.Direction direction = sort.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "id"));
    Page<Meeting> pageList = this.meetingRepo.findByTopicId(topicId, pageable);
    Metadata metadata = new Metadata();
    metadata.setPage(pageable.getPageNumber());
    metadata.setPageSize(pageable.getPageSize());

    metadata.setTotalPage(pageList.getTotalPages());
    metadata.setTotalElement(pageList.getTotalElements());

    RestResponse<List<Meeting>> restResponse  = new RestResponse<>();
    restResponse.setMetadata(metadata);
    restResponse.setMessage("Lấy lịch họp thành công");
    restResponse.setData(pageList.getContent());
    return restResponse;
  }

  public ApiResponseStudent<List<Evaluation>> handleGetEvaluation (Long topicId) {
    List<Evaluation> evaluationDB = this.evaluationRepo.findByTopicId(topicId);
    ApiResponseStudent<List<Evaluation>> apiResponseGetEvaluation = new ApiResponseStudent<>();
    apiResponseGetEvaluation.setData(evaluationDB);
    apiResponseGetEvaluation.setMessage("Lấy đánh giá đề tài thành công");
    return apiResponseGetEvaluation;
  }

}
