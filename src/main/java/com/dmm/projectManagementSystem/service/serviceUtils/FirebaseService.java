package com.dmm.projectManagementSystem.service.serviceUtils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FirebaseService {

    boolean deleteFile(String fileUrl);
    List<String> uploadFiles(MultipartFile[] files) throws IOException;

}
