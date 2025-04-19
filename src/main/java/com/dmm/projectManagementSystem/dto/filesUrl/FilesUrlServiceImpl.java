package com.dmm.projectManagementSystem.dto.filesUrl;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.model.FilesUrl;
import com.dmm.projectManagementSystem.repo.FilesUrlRepo;
import com.dmm.projectManagementSystem.service.instructor.filesUrlManagement.FilesUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FilesUrlServiceImpl implements FilesUrlService {
    @Autowired
    private FilesUrlRepo filesUrlRepo;
    @Override
    public ApiResponse<List<FilesUrlDTO>> getFilesUrl(Long id) {
        List<FilesUrl> list= filesUrlRepo.findByTopicId(id);
        List<FilesUrlDTO> res= list.stream().map(x->new FilesUrlDTO(x.getId(),x.getUri(),x.getTopic().getName())).toList();

        return new ApiResponse<>(1000,"Thành công",res);
    }
}
