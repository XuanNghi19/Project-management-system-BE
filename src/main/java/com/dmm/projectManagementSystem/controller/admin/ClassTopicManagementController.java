package com.dmm.projectManagementSystem.controller.admin;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.classTopic.ClassTopicDetailResponse;
import com.dmm.projectManagementSystem.dto.classTopic.ClassTopicListByPageResponse;
import com.dmm.projectManagementSystem.dto.classTopic.CreateClassTopicRequest;
import com.dmm.projectManagementSystem.dto.classTopic.UpdateClassTopicRequest;
import com.dmm.projectManagementSystem.service.admin.classTopicManagement.ClassTopicManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/class_topic_management")
public class ClassTopicManagementController {

    final private ClassTopicManagementService classTopicManagementService;

    @Operation(summary = "them lop hoc do an")
    @PostMapping("add_class_topic")
    public ApiResponse<?> addClassTopic(
            @RequestBody @Valid CreateClassTopicRequest request,
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
            Pair<String, Boolean> response = classTopicManagementService.addClassTopic(request);
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

    @Operation(summary = "Cập nhật lop hoc do an")
    @PutMapping(value = "/update_class_topic")
    public ApiResponse<?> updateClassTopic(
            @RequestBody @Valid UpdateClassTopicRequest request,
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
            Pair<String, Boolean> response = classTopicManagementService.updateClassTopic(request);
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

    @Operation(summary = "Xóa lop hoc do an")
    @DeleteMapping("/delete_class_topic")
    public ApiResponse<?> deleteClassTopic(
            @Parameter(description = "mã của lop hoc do an")
            @RequestParam(value = "id class topic") Long id
    ) {
        try {
            Pair<String, Boolean> response = classTopicManagementService.deleteClassTopic(id);
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

    @Operation(summary = "Lấy tất cả lop hoc do an theo trang - có phân trang")
    @GetMapping("/get_all_class_topic")
    public ApiResponse<?> getAllClassTopic(
            @Parameter(description = "tên lop hoc do an")
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
            ClassTopicListByPageResponse response = classTopicManagementService.getAllClassTopic(
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

    @Operation(summary = "xem chi tiet lop hoc do an")
    @GetMapping("/get_detail_class_topic")
    public ApiResponse<?> getClassTopicDetail(
            @Parameter(description = "mã của lop hoc do an")
            @RequestParam(value = "id class topic") Long idNum
    ) {
        try {
            ClassTopicDetailResponse response = classTopicManagementService.getClassTopicDetail(idNum);
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
