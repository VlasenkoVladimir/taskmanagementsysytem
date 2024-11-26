package effectiveMobile.com.taskManagementSystem.dto;

import java.util.List;

public record UserDto(
        Long id,
        String email,
        String password,
        RoleDto roleDto,
        List<Long> authorTaskList,
        List<Long> executorTaskList,
        String changePasswordToken) {
}