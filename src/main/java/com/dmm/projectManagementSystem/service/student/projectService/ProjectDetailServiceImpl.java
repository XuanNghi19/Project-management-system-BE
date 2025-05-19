package com.dmm.projectManagementSystem.service.student.projectService;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.Metadata;
import com.dmm.projectManagementSystem.dto.project.CouncilResDTO;
import com.dmm.projectManagementSystem.dto.project.DefenseScheduleResDTO;
import com.dmm.projectManagementSystem.model.*;
import com.dmm.projectManagementSystem.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectDetailServiceImpl implements ProjectDetailService{
  private final TopicRepo topicRepo;
  private final DefenseScheduleRepo defenseScheduleRepo;
  private final MeetingRepo meetingRepo;
  private final CouncilRepo councilRepo;
  private final EvaluationRepo evaluationRepo;
  private final TaskRepo taskRepo;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ApiResponseStudent<DefenseScheduleResDTO> handleGetDefenseSchedule (Long topicId) {
    Optional<DefenseSchedule> defenseScheduleDB = this.defenseScheduleRepo.findByTopicId(topicId);
    defenseScheduleDB.orElseThrow(() -> new NoSuchElementException("Không tìm thấy topic trong DB !"));
    DefenseScheduleResDTO defenseScheduleResDTO = new DefenseScheduleResDTO();
    ApiResponseStudent<DefenseScheduleResDTO> apiResponseGetDefence  = new ApiResponseStudent<>();
    apiResponseGetDefence.setData(defenseScheduleResDTO.convertToDefenseScheduleDTO(defenseScheduleDB.get()));
    apiResponseGetDefence.setMessage("Lấy lịch bảo về đồ án thành công !");
    return apiResponseGetDefence;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
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
            .topicSemester(council.getTopicSemester())
            .departmentName(council.getDepartment().getName())
            .endTime(council.getEndTime())
            .build());
    apiResponseGetCouncil.setMessage("Lấy hội đồng tham gia đánh giá đồ án thành công");
    return apiResponseGetCouncil;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ApiResponseStudent<List<Meeting>> handleGetMeeting (Long topicId, int page, int size, String sort) {

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

    ApiResponseStudent<List<Meeting>> apiResponseGetMeeting  = new ApiResponseStudent<>();
    apiResponseGetMeeting.setMetadata(metadata);
    apiResponseGetMeeting.setMessage("Lấy lịch họp thành công");
    apiResponseGetMeeting.setData(pageList.getContent());
    return apiResponseGetMeeting;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ApiResponseStudent<List<Evaluation>> handleGetEvaluation (Long topicId) {
    List<Evaluation> evaluationDB = this.evaluationRepo.findByTopicId(topicId);
    ApiResponseStudent<List<Evaluation>> apiResponseGetEvaluation = new ApiResponseStudent<>();
    apiResponseGetEvaluation.setData(evaluationDB);
    apiResponseGetEvaluation.setMessage("Lấy đánh giá đề tài thành công");
    return apiResponseGetEvaluation;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ApiResponseStudent<List<Task>> handleGetTask (Long topicId, int page, int size, String sort) {
    Topic topicDB = this.topicRepo.findById(topicId)
    .orElseThrow(() -> new NoSuchElementException("Không tìm thấy chủ đề trong DB !"));

    Sort.Direction direction = sort.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "id"));
    Page<Task> pageList = this.taskRepo.findByTopicId(topicId, pageable);
    Metadata metadata = new Metadata();
    metadata.setPage(pageable.getPageNumber());
    metadata.setPageSize(pageable.getPageSize());

    metadata.setTotalPage(pageList.getTotalPages());
    metadata.setTotalElement(pageList.getTotalElements());

    ApiResponseStudent<List<Task>> apiResponseGetTask  = new ApiResponseStudent<>();
    apiResponseGetTask.setMetadata(metadata);
    apiResponseGetTask.setMessage("Lấy nhiệm vụ được giao thành công");
    apiResponseGetTask.setData(pageList.getContent());
    return apiResponseGetTask;
  }

}
