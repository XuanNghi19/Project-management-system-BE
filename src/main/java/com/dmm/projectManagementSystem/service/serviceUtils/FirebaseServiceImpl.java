package com.dmm.projectManagementSystem.service.serviceUtils;

import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FirebaseServiceImpl implements FirebaseService{

    @Override
    public boolean deleteFile(String fileUrl) {
        try {
            // Lấy bucket từ Firebase Storage
            Bucket bucket = StorageClient.getInstance().bucket();

            // Trích xuất đường dẫn file từ URL Firebase Storage
            String filePath = extractFilePathFromUrl(fileUrl);

            if (filePath != null) {
                // Xóa file khỏi Firebase Storage
                boolean deleted = bucket.get(filePath).delete();
                return deleted;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<String> uploadFiles(MultipartFile[] files) throws IOException {
        List<String> fileUrls = new ArrayList<>();
        Bucket bucket = StorageClient.getInstance().bucket();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                // Tạo tên file duy nhất
                String fileName = "project_management/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

                // Upload file lên Firebase Storage
                bucket.create(fileName, file.getBytes(), file.getContentType());

                // Encode tên file
                String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replace("+", "%20");

                // Lưu URL của file vào danh sách
                String fileUrl = "https://firebasestorage.googleapis.com/v0/b/" + bucket.getName() + "/o/" + encodedFileName + "?alt=media";
                fileUrls.add(fileUrl);
            }
        }
        return fileUrls;
    }

    private String extractFilePathFromUrl(String fileUrl) {
        try {
            // URL mẫu: https://firebasestorage.googleapis.com/v0/b/<BUCKET_NAME>/o/project_management%2Ffile.png?alt=media
            // cắt chuỗi chia lam 2 phan
            String[] parts = fileUrl.split("/o/");
            if (parts.length > 1) {
                // chia lam 2 phan va lay phan dau tien
                String filePath = parts[1].split("\\?alt=media")[0];
                return java.net.URLDecoder.decode(filePath, "UTF-8"); // Giải mã tên file
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
