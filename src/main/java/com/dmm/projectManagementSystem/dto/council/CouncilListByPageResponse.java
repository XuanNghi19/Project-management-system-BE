package com.dmm.projectManagementSystem.dto.council;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.course.CourseListByPageResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CouncilListByPageResponse {
    List<CouncilResponse> list;

    int totalPage;
    int crrPage;
    int limit;

    static public CouncilListByPageResponse fromSplitPage(
            List<CouncilResponse> list,
            int totalPage,
            int crrPage,
            int limit
    ) {
        return CouncilListByPageResponse.builder()
                .list(list)
                .totalPage(totalPage)
                .crrPage(crrPage)
                .limit(limit)
                .build();
    }
}
