package com.dmm.projectManagementSystem.controller;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.department.DepartmentListByPageResponse;
import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.major.MajorListByPageResponse;
import com.dmm.projectManagementSystem.service.admin.majorManagement.MajorManagementService;
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
@RequestMapping("${api.prefix}/major_management")
public class MajorManagementController {

    final private MajorManagementService majorManagementService;

    @Operation(summary = "them nganh hoc, id mặc định của viêc tạo ngành học là null - id: null")
    @PostMapping("add_major")
    public ApiResponse<?> addMajor(
            @RequestBody @Valid List<CRUDMajor> requests,
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
            boolean response = majorManagementService.addMajor(requests);
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

    @Operation(summary = "Cập nhật nganh hoc")
    @PutMapping(value = "/update_major")
    public ApiResponse<?> updateMajor(
            @RequestBody @Valid CRUDMajor request,
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
            boolean response = majorManagementService.updateMajor(request);
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


    @Operation(summary = "Xóa nganh hoc")
    @DeleteMapping("/delete_major")
    public ApiResponse<?> deleteMajor(
            @Parameter(description = "id cua nganh hoc")
            @RequestParam(value = "id major") Long id
    ) {
        try {
            Pair<String, Boolean> response = majorManagementService.deleteMajor(id);
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

    @Operation(summary = "Lấy tất cả nganh hoc theo trang - có phân trang")
    @GetMapping("/get_all_major")
    public ApiResponse<?> getAllMajor(
            @Parameter(description = "ten nganh hoc")
            @RequestParam(value = "name", required = false) String name,

            @Parameter(description = "id khoa")
            @RequestParam(value = "id department", required = false) Long departmentID,

            @Parameter(description = "số trang muốn chọn")
            @RequestParam(value = "page") int page,

            @Parameter(description = "số khoa trong một trang")
            @RequestParam(value = "limit") int limit
    ) {
        try {
            MajorListByPageResponse response = majorManagementService.getAllMajor(
                    name,
                    departmentID,
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
