//package com.dmm.projectManagementSystem.config;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//@Configuration
//public class FirebaseConfig {
//
//    @Bean
//    public FirebaseApp initializeFirebase() throws IOException {
//        File file = new File("firebase_service.json");
//        if(!file.exists()) throw new IOException("File does not exists!");
//
//        FileInputStream serviceAccount = new FileInputStream(file);
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setStorageBucket("fakeslink-89c91.appspot.com")
//                .build();
//
//        return FirebaseApp.initializeApp(options);
//    }
//}
//
