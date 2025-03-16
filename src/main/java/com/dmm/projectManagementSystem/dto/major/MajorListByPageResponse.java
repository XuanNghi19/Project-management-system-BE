package com.dmm.projectManagementSystem.dto.major;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.department.DepartmentListByPageResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class MajorListByPageResponse {
    List<CRUDMajor> list;

    int totalPage;
    int crrPage;
    int limit;

    static public MajorListByPageResponse fromSplitPage(
            List<CRUDMajor> list,
            int totalPage,
            int crrPage,
            int limit
    ) {
        return MajorListByPageResponse.builder()
                .list(list)
                .totalPage(totalPage)
                .crrPage(crrPage)
                .limit(limit)
                .build();
    }
}
