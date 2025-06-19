package com.dmm.projectManagementSystem.controller.instructor;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.filesUrl.FilesUrlDTO;
import com.dmm.projectManagementSystem.dto.filesUrl.FilesUrlServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilesUrlController {
    @Autowired
    private FilesUrlServiceImpl filesUrlService;

    @GetMapping("/files/{id}")
    public ResponseEntity<ApiResponse<List<FilesUrlDTO>>> getFilesUrl(@PathVariable("id") Long id){
        return ResponseEntity.ok(filesUrlService.getFilesUrl(id));
    }
}
