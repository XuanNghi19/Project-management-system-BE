package com.dmm.projectManagementSystem.service.student.teamService;

public interface TeamService {
    public boolean handleCreateGroup(Long userID, String groupName);

    public boolean handleRemoveStudentFromGroup();

    public boolean handleDeleteGroup(Long id);
}
