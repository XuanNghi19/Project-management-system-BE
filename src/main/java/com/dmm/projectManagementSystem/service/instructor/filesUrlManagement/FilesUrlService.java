package com.dmm.projectManagementSystem.service.instructor.filesUrlManagement;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.filesUrl.FilesUrlDTO;

import java.util.List;

public interface FilesUrlService {
    ApiResponse<List<FilesUrlDTO>> getFilesUrl(Long id);
}
