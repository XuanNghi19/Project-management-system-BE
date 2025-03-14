package com.dmm.projectManagementSystem.dto.department;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class DepartmentListByPageResponse {
    List<CRUDDepartment> list;

    int totalPage;
    int crrPage;
    int limit;

    static public DepartmentListByPageResponse fromSplitPage(
            List<CRUDDepartment> list,
            int totalPage,
            int crrPage,
            int limit
    ) {
        return DepartmentListByPageResponse.builder()
                .list(list)
                .totalPage(totalPage)
                .crrPage(crrPage)
                .limit(limit)
                .build();
    }
}
