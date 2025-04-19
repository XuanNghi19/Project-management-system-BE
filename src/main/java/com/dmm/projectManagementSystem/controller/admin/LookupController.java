package com.dmm.projectManagementSystem.controller.admin;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
import com.dmm.projectManagementSystem.service.serviceUtils.LookupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/lookup")
public class LookupController {

    final private LookupService lookupService;

    @Operation(summary = "lay thong tin tim kiem khoa theo ten")
    @GetMapping("search_department")
    public ApiResponse<?> searchDepartment(
            @Parameter(description = "search_value")
            @RequestParam(value = "name", required = false) String name
    ) {
        try {
            List<CRUDDepartment> response = lookupService.searchDepartment(name);
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

    @Operation(summary = "lay thong tin tim kiem nganh hoc theo ten")
    @GetMapping("search_major")
    public ApiResponse<?> searchMajor(
            @Parameter(description = "search_value")
            @RequestParam(value = "name", required = false) String name
    ) {
        try {
            List<CRUDMajor> response = lookupService.searchMajor(name);
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

    @Operation(summary = "lay thong tin tim kiem ki hoc do an theo ten")
    @GetMapping("search_topic_semester")
    public ApiResponse<?> searchTopicSemester(
            @Parameter(description = "search_value")
            @RequestParam(value = "name", required = false) String name
    ) {
        try {
            List<CRUDTopicSemester> response = lookupService.searchTopicSemester(name);
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

    @Operation(summary = "lay thong tin tim kiem nien khoa theo ten")
    @GetMapping("search_course")
    public ApiResponse<?> searchCourse(
            @Parameter(description = "search_value")
            @RequestParam(value = "name", required = false) String name
    ) {
        try {
            List<CRUDCourse> response = lookupService.searchCourse(name);
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
