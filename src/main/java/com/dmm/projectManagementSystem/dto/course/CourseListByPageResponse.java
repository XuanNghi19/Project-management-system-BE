package com.dmm.projectManagementSystem.dto.course;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CourseListByPageResponse {
    List<CRUDCourse> list;

    int totalPage;
    int crrPage;
    int limit;

    static public CourseListByPageResponse fromSplitPage(
            List<CRUDCourse> list,
            int totalPage,
            int crrPage,
            int limit
    ) {
        return CourseListByPageResponse.builder()
                .list(list)
                .totalPage(totalPage)
                .crrPage(crrPage)
                .limit(limit)
                .build();
    }
}
