package com.dmm.projectManagementSystem.service.serviceUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LocalFileService {
    private final String uploadDir = "uploads"; // Thư mục lưu file (tạo cùng cấp với project hoặc tùy ý)

    public String saveFile(MultipartFile file) throws IOException {
        // Tạo thư mục nếu chưa tồn tại
        File dir = new File(uploadDir);
        if (!dir.exists())
            dir.mkdirs();

        // Đường dẫn file lưu
        String filePath = uploadDir + File.separator + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(filePath);

        // Lưu file
        Files.write(path, file.getBytes());

        // Trả về đường dẫn file (hoặc tên file, hoặc URL nếu cần)
        return filePath;
    }

    public boolean deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}