//package com.dmm.projectManagementSystem.service.student.teamService;
//
//
//import org.apache.tomcat.util.http.MimeHeaders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.MailException;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMailMessage;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.mail.javamail.MimeMessagePreparator;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendInvitationEmail (String recepientEmail, String groupName, String senderName) {
//        MimeMessagePreparator preparator = mimeMessage -> {
//            MimeMessageHelper helper =  new MimeMessageHelper(mimeMessage, true, "UTF-8");
//            helper.setTo(recepientEmail);
//            helper.setSubject("Lời mời tham gia nhóm làm đồ án");
//
//            String content = "<p>Chào bạn,</p>" +
//                    "<p><strong>" + senderName + "</strong> đã mời bạn tham gia nhóm đồ án <strong>" + groupName + "</strong>.</p>" +
//                    "<p>Vui lòng đăng nhập vào hệ thống để xác nhận lời mời.</p>" +
//                    "<p>Trân trọng,<br>Hệ thống quản lý đồ án</p>";
//
//        helper.setText(content, true);
//        };
//
//        try {
//            mailSender.send(preparator);
//            System.out.println("Mail đã được gửi tới " + recepientEmail);
//
//
//        }catch (MailException ex){
//            System.out.println("Lỗi gửi email : " +ex.getMessage());
//
//        }
//    }
//}
