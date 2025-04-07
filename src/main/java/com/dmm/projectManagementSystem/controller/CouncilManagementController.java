package com.dmm.projectManagementSystem.controller;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.council.CouncilDetailResponse;
import com.dmm.projectManagementSystem.dto.council.CouncilListByPageResponse;
import com.dmm.projectManagementSystem.dto.council.CreateCouncilRequest;
import com.dmm.projectManagementSystem.dto.council.UpdateCouncilRequest;
import com.dmm.projectManagementSystem.service.admin.councilManagement.CouncilManagementService;
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
@RequestMapping("${api.prefix}/council_management")
public class CouncilManagementController {

    final private CouncilManagementService councilManagementService;

    @Operation(summary = "them hoi dong")
    @PostMapping("add_council")
    public ApiResponse<?> addCouncil(
            @RequestBody @Valid CreateCouncilRequest request,
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
            Pair<String, Boolean> response = councilManagementService.addCouncil(request);
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

    @Operation(summary = "Cập nhat hoi dong")
    @PutMapping(value = "/update_council")
    public ApiResponse<?> updateCouncil(
            @RequestBody @Valid UpdateCouncilRequest request,
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
            Pair<String, Boolean> response = councilManagementService.updateCouncil(request);
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

    @Operation(summary = "Xóa hoi dong")
    @DeleteMapping("/delete_council")
    public ApiResponse<?> deleteCouncil(
            @Parameter(description = "mã hoi dong")
            @RequestParam(value = "id council") Long id
    ) {
        try {
            Pair<String, Boolean> response = councilManagementService.deleteCouncil(id);
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

    @Operation(summary = "Lấy tất cả hoi dong theo trang - có phân trang")
    @GetMapping("/get_all_council")
    public ApiResponse<?> getAllCouncil(
            @Parameter(description = "tên hoi dong")
            @RequestParam(value = "name", required = false) String name,

            @Parameter(description = "mã của kỳ học đồ án")
            @RequestParam(value = "topic semester id", required = false) Long topicSemesterID,

            @Parameter(description = "mã của kho")
            @RequestParam(value = "department id", required = false) Long departmentID,

            @Parameter(description = "thoi gian bat dau phien lam viec cua hoi dong - định dạng: yyyy-MM-dd HH:mm:ss")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "start", required = false) LocalDateTime startTime,

            @Parameter(description = "thoi gian ket thuc phien lam viec cua hoi dong - định dạng: yyyy-MM-dd HH:mm:ss")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "end", required = false) LocalDateTime endTime,

            @Parameter(description = "số trang muốn chọn")
            @RequestParam(value = "page") int page,

            @Parameter(description = "số user trong một trang")
            @RequestParam(value = "limit") int limit
    ) {
        try {
            CouncilListByPageResponse response = councilManagementService.getAllCouncil(
                    name,
                    topicSemesterID,
                    departmentID,
                    startTime,
                    endTime,
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


    @Operation(summary = "xem chi tiet hoi dong")
    @GetMapping("/get_council_detail")
    public ApiResponse<?> getCouncilDetail(
            @Parameter(description = "mã của hoi dong")
            @RequestParam(value = "id council") Long idNum
    ) {
        try {
            CouncilDetailResponse response = councilManagementService.getCouncilDetail(idNum);
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
