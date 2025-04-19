package com.dmm.projectManagementSystem.controller.admin;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.course.CourseListByPageResponse;
import com.dmm.projectManagementSystem.service.admin.courseManagement.CourseManagementService;
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
@RequestMapping("${api.prefix}/course_management")
public class CourseManagementController {

    final private CourseManagementService courseManagementService;

    @Operation(summary = "them nien khoa, , id mặc định của viêc tạo niên khóa là null - id: null")
    @PostMapping("add_course")
    public ApiResponse<?> addCourse(
            @RequestBody @Valid List<CRUDCourse> requests,
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
            boolean response = courseManagementService.addCourse(requests);
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

    @Operation(summary = "Cập nhật nien khoa")
    @PutMapping(value = "/update_course")
    public ApiResponse<?> updateCourse(
            @RequestBody @Valid CRUDCourse request,
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
            boolean response = courseManagementService.updateCourse(request);
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


    @Operation(summary = "Xóa nien khoa")
    @DeleteMapping("/delete_course")
    public ApiResponse<?> deleteCourse(
            @Parameter(description = "id cua nien khoa")
            @RequestParam(value = "id course") Long id
    ) {
        try {
            Pair<String, Boolean> response = courseManagementService.deleteCourse(id);
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

    @Operation(summary = "Lấy tất cả nien khoa theo trang - có phân trang")
    @GetMapping("/get_all_course")
    public ApiResponse<?> getAllCourse(
            @Parameter(description = "ten nien khoa")
            @RequestParam(value = "name", required = false) String name,

            @Parameter(description = "ngay bat dau nien khoa - định dạng: yyyy-MM-dd HH:mm:ss")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "start", required = false) LocalDateTime start,

            @Parameter(description = "ngay ket thuc nien khoa - định dạng: yyyy-MM-dd HH:mm:ss")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "end", required = false) LocalDateTime end,

            @Parameter(description = "số trang muốn chọn")
            @RequestParam(value = "page") int page,

            @Parameter(description = "số khoa trong một trang")
            @RequestParam(value = "limit") int limit
    ) {
        try {
            CourseListByPageResponse response = courseManagementService.getAllCourse(
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
