package com.dmm.projectManagementSystem.controller.admin;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.user.CreateUserRequest;
import com.dmm.projectManagementSystem.dto.user.UpdateUserRequest;
import com.dmm.projectManagementSystem.dto.user.UserListByPageResponse;
import com.dmm.projectManagementSystem.enums.Role;
import com.dmm.projectManagementSystem.service.admin.userManagement.UserManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/user_management")
public class UserManagementController {

    final private UserManagementService userManagementService;

    @Operation(summary = "Thêm giảng viên hướng dẫn")
    @PostMapping("/add_teacher")
    public ApiResponse<?> addTeacher(
             @RequestBody @Valid List<CreateUserRequest> requests,
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
            boolean response = userManagementService.addTeachers(requests);
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

    @Operation(summary = "Thêm sinh viên")
    @PostMapping("/add_student")
    public ApiResponse<?> addStudent(
            @RequestBody @Valid List<CreateUserRequest> requests,
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
            boolean response = userManagementService.addStudent(requests);
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

    @Operation(summary = "Thêm quản trị viên")
    @PostMapping("/add_admin")
    public ApiResponse<?> addAdmin(
            @RequestBody @Valid CreateUserRequest request,
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
            boolean response = userManagementService.addAdmin(request);
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

    @Operation(summary = "Cập nhật người dùng")
    @PutMapping(value = "/update_user")
    public ApiResponse<?> updateUser(
            @RequestBody @Valid UpdateUserRequest request,
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
            boolean response = userManagementService.updateUser(request);
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

    @Operation(summary = "Xóa người dùng")
    @DeleteMapping("/delete_user")
    public ApiResponse<?> deleteUser(
            @RequestParam(value = "idNum_user") String idNum
    ) {
        try {
            boolean response = userManagementService.deleteUser(idNum);
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

    @Operation(summary = "Thay đổi mật khẩu người dùng")
    @PatchMapping("/change_password")
    public ApiResponse<?> changePassword(
            @RequestParam(value = "idNum_user") String idNum,
            @RequestParam(value = "new_password") String newPassword
    ) {
        try {
            boolean response = userManagementService.changePassword(newPassword, idNum);
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

    @Operation(summary = "Cập nhật ảnh người dùng")
    @PatchMapping(value = "/upload_avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> uploadAvatar(
            @RequestParam(value = "idNum_user") String idNum,
            @RequestPart(value = "avatar_img") MultipartFile[] avatars
    ) {
        try {
            boolean response = userManagementService.uploadAvatar(idNum, avatars);
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

    @Operation(summary = "Lấy tất cả người dùng theo trang - có phân trang")
    @GetMapping("/get_all_user")
    public ApiResponse<?> getAllUser(
            @Parameter(description = "vai trò")
            @RequestParam(value = "role") Role role,

            @Parameter(description = "id của khoa")
            @RequestParam(value = "department_id", required = false) Long departmentId,

            @Parameter(description = "id của ngành học")
            @RequestParam(value = "major_id", required = false) Long majorId,

            @Parameter(description = "id của khóa học")
            @RequestParam(value = "course_id", required = false) Long courseId,

            @Parameter(description = "tên người dùng")
            @RequestParam(value = "name", required = false) String name,

            @Parameter(description = "số trang muốn chọn")
            @RequestParam(value = "page") int page,

            @Parameter(description = "số user trong một trang")
            @RequestParam(value = "limit") int limit
    ) {
        try {
            UserListByPageResponse response = userManagementService.getAllUser(
                    role,
                    departmentId,
                    majorId,
                    courseId,
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
