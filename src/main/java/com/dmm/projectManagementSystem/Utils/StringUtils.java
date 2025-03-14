package com.dmm.projectManagementSystem.Utils;

public class StringUtils {

    // lay chu cai dau o nhung tu trong chuoi
    public static String getInitials(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        StringBuilder initials = new StringBuilder();
        for (String word : input.split("\\s+")) { // Tách theo khoảng trắng
            if (!word.isEmpty()) {
                initials.append(word.charAt(0));
            }
        }
        return initials.toString().toUpperCase();
    }

    // lay vai chu cai dau
    public static String getFirstNChars(String input, int n) {
        if (input == null || input.length() < n) {
            return input;
        }
        return input.substring(0, n);
    }
}
