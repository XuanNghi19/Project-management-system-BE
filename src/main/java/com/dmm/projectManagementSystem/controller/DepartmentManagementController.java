package com.dmm.projectManagementSystem.controller;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.department.DepartmentListByPageResponse;
import com.dmm.projectManagementSystem.dto.user.UpdateUserRequest;
import com.dmm.projectManagementSystem.dto.user.UserListByPageResponse;
import com.dmm.projectManagementSystem.enums.Role;
import com.dmm.projectManagementSystem.service.admin.departmentManagement.DepartmentManagementService;
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
@RequestMapping("${api.prefix}/department_management")
public class DepartmentManagementController {

    final private DepartmentManagementService departmentManagementService;

    @Operation(summary = "them khoa, id mặc định của viêc tạo khoa là null - id: null")
    @PostMapping("add_department")
    public ApiResponse<?> addDepartment(
            @RequestBody @Valid List<CRUDDepartment> requests,
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
            boolean response = departmentManagementService.addDepartment(requests);
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

    @Operation(summary = "Cập nhật khoa")
    @PutMapping(value = "/update_department")
    public ApiResponse<?> updateDepartment(
            @RequestBody @Valid CRUDDepartment request,
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
            boolean response = departmentManagementService.updateDepartment(request);
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


    @Operation(summary = "Xóa khoa")
    @DeleteMapping("/delete_department")
    public ApiResponse<?> deleteDepartment(
            @Parameter(description = "id cua khoa")
            @RequestParam(value = "id department") Long id
    ) {
        try {
            Pair<String, Boolean> response = departmentManagementService.deleteDepartment(id);
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

    @Operation(summary = "Lấy tất cả khoa theo trang - có phân trang")
    @GetMapping("/get_all_department")
    public ApiResponse<?> getAllDepartment(
            @Parameter(description = "ten khoa")
            @RequestParam(value = "name", required = false) String name,

            @Parameter(description = "số trang muốn chọn")
            @RequestParam(value = "page") int page,

            @Parameter(description = "số khoa trong một trang")
            @RequestParam(value = "limit") int limit
    ) {
        try {
            DepartmentListByPageResponse response = departmentManagementService.getAllDepartment(
                    name,
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
