package com.dmm.projectManagementSystem.controller.admin;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
import com.dmm.projectManagementSystem.dto.topicSemester.TopicSemesterListByPageResponse;
import com.dmm.projectManagementSystem.service.admin.topicSemesterManagement.TopicSemesterManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/topic_semester_management")
public class TopicSemesterManagementController {

    final private TopicSemesterManagementService topicSemesterManagementService;

    @Operation(summary = "them ky hoc do an, id mặc định của viêc kỳ học đồ án là null - id: null")
    @PostMapping("add_topic_semester")
    public ApiResponse<?> addTopicSemester(
            @RequestBody @Valid List<CRUDTopicSemester> requests,
            BindingResult result
    ) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ApiResponse.<Boolean>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(errorMessages.toString())
                    .result(false)
                    .build();
        }

        try {
            boolean response = topicSemesterManagementService.addTopicSemester(requests);
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

    @Operation(summary = "Cập nhật ky hoc do an")
    @PutMapping(value = "/update_topic_semester")
    public ApiResponse<?> updateTopicSemester(
            @RequestBody @Valid CRUDTopicSemester request,
            BindingResult result
    ) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ApiResponse.<Boolean>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(errorMessages.toString())
                    .result(false)
                    .build();
        }

        try {
            boolean response = topicSemesterManagementService.updateTopicSemester(request);
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


    @Operation(summary = "Xóa ky hoc do an")
    @DeleteMapping("/delete_topic_semester")
    public ApiResponse<?> deleteTopicSemester(
            @Parameter(description = "id cua ky hoc do an")
            @RequestParam(value = "id TopicSemester") Long id
    ) {
        try {
            Pair<String, Boolean> response = topicSemesterManagementService.deleteTopicSemester(id);
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

    @Operation(summary = "Lấy tất cả ky hoc do an theo trang - có phân trang")
    @GetMapping("/get_all_topic_semester")
    public ApiResponse<?> getAllTopicSemester(
            @Parameter(description = "ten ky hoc do an")
            @RequestParam(value = "name", required = false) String name,

            @Parameter(description = "ngay bat dau ky hoc - định dạng: yyyy-MM-dd HH:mm:ss")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "start", required = false) LocalDateTime start,

            @Parameter(description = "ngay ket thuc ky hoc - định dạng: yyyy-MM-dd HH:mm:ss")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "end", required = false) LocalDateTime end,

            @Parameter(description = "số trang muốn chọn")
            @RequestParam(value = "page") int page,

            @Parameter(description = "số khoa trong một trang")
            @RequestParam(value = "limit") int limit
    ) {
        try {
            TopicSemesterListByPageResponse response = topicSemesterManagementService.getAllTopicSemester(
                    name,
                    start,
                    end,
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

}
