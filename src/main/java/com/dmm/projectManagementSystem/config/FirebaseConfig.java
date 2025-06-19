package com.dmm.projectManagementSystem.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        File file = new File("firebase_service.json");
        if (!file.exists()) throw new IOException("File does not exist!");

        FileInputStream serviceAccount = new FileInputStream(file);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("fakeslink-89c91.appspot.com")
                .build();

        // ✅ Chỉ khởi tạo nếu chưa tồn tại FirebaseApp nào
        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        } else {
            return FirebaseApp.getInstance(); // Lấy app mặc định đã được khởi tạo
        }
    }
}


