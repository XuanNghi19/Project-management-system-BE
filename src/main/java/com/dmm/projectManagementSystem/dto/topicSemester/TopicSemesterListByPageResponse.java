package com.dmm.projectManagementSystem.dto.topicSemester;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TopicSemesterListByPageResponse {
    List<CRUDTopicSemester> list;

    int totalPage;
    int crrPage;
    int limit;

    static public TopicSemesterListByPageResponse fromSplitPage(
            List<CRUDTopicSemester> list,
            int totalPage,
            int crrPage,
            int limit
    ) {
        return TopicSemesterListByPageResponse.builder()
                .list(list)
                .totalPage(totalPage)
                .crrPage(crrPage)
                .limit(limit)
                .build();
    }
}
