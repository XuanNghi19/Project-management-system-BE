package com.dmm.projectManagementSystem.controller.admin;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.topic.TopicDetailsResponse;
import com.dmm.projectManagementSystem.dto.topic.TopicListByPageResponse;
import com.dmm.projectManagementSystem.service.admin.topicManagement.TopicManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/topic_management")
public class TopicManagementController {

    final private TopicManagementService topicManagementService;

    @Operation(summary = "Duyệt điểm đề tài")
    @PatchMapping(value = "/approve_grade")
    public ApiResponse<?> approveGrade(
            @Parameter(description = "mã của đề tài")
            @RequestParam(value = "idNum topic") String idNum
    ) {
        try {
            Pair<Pair<String, Boolean>, Double> response = topicManagementService.approveGrade(idNum);
            return ApiResponse.builder()
                    .code(HttpStatus.OK.value())
                    .result(response.getSecond())
                    .message(String.format(response.getFirst().getFirst() + ", result: " + response.getFirst().getSecond()))
                    .build();
        } catch (Exception ex) {
            return ApiResponse.<String>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.BAD_REQUEST.toString())
                    .result(ex.toString())
                    .build();
        }
    }

    @Operation(summary = "Xóa đề tài")
    @DeleteMapping("/delete_topic")
    public ApiResponse<?> deleteTopic(
            @Parameter(description = "mã của đề tài")
            @RequestParam(value = "idNum topic") String idNum
    ) {
        try {
            Pair<String, Boolean> response = topicManagementService.deleteTopic(idNum);
            return ApiResponse.builder()
                    .code(HttpStatus.OK.value())
                    .result(response.getSecond())
                    .message(response.getFirst())
                    .build();
        } catch (Exception ex) {
            return ApiResponse.<String>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.BAD_REQUEST.toString())
                    .result(ex.toString())
                    .build();
        }
    }


    @Operation(summary = "Lấy tất cả đề tài theo trang - có phân trang")
    @GetMapping("/get_all_topic")
    public ApiResponse<?> getAllTopic(
            @Parameter(description = "tên đề tài")
            @RequestParam(value = "name", required = false) String name,

            @Parameter(description = "mã của kỳ học đồ án")
            @RequestParam(value = "topic semester id", required = false) Long topicSemesterID,

            @Parameter(description = "mã của ngành học")
            @RequestParam(value = "major id", required = false) Long majorID,

            @Parameter(description = "số trang muốn chọn")
            @RequestParam(value = "page") int page,

            @Parameter(description = "số user trong một trang")
            @RequestParam(value = "limit") int limit
    ) {
        try {
            TopicListByPageResponse response = topicManagementService.getAllTopic(
                    name,
                    topicSemesterID,
                    majorID,
                    page,
                    limit
            );
            return ApiResponse.builder()
                    .code(HttpStatus.OK.value())
                    .result(response)
                    .message(HttpStatus.OK.toString())
                    .build();
        } catch (Exception ex) {
            return ApiResponse.<String>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.BAD_REQUEST.toString())
                    .result(ex.toString())
                    .build();
        }
    }

    @Operation(summary = "xem chi tiết đề tài")
    @GetMapping("/get_detail_topic")
    public ApiResponse<?> getDetailTopic(
            @Parameter(description = "mã của đề tài")
            @RequestParam(value = "idNum topic") String idNum
    ) {
        try {
            TopicDetailsResponse response = topicManagementService.getDetailTopic(idNum);
            return ApiResponse.builder()
                    .code(HttpStatus.OK.value())
                    .result(response)
                    .message(HttpStatus.OK.toString())
                    .build();
        } catch (Exception ex) {
            return ApiResponse.<String>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.BAD_REQUEST.toString())
                    .result(ex.toString())
                    .build();
        }
    }

}
