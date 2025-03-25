package com.dmm.projectManagementSystem.dto.classTopic;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClassTopicListByPageResponse {
    List<ClassTopicResponse> list;

    int totalPage;
    int crrPage;
    int limit;

    static public ClassTopicListByPageResponse fromSplitPage(
            List<ClassTopicResponse> list,
            int totalPage,
            int crrPage,
            int limit
    ) {
        return ClassTopicListByPageResponse.builder()
                .list(list)
                .totalPage(totalPage)
                .crrPage(crrPage)
                .limit(limit)
                .build();
    }
}
