package com.dmm.projectManagementSystem.dto.topic;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TopicListByPageResponse {
    List<TopicResponse> list;

    int totalPage;
    int crrPage;
    int limit;

    static public TopicListByPageResponse fromSplitPage(
            List<TopicResponse> list,
            int totalPage,
            int crrPage,
            int limit
    ) {
        return TopicListByPageResponse.builder()
                .list(list)
                .totalPage(totalPage)
                .crrPage(crrPage)
                .limit(limit)
                .build();
    }
}
