package effectiveMobile.com.taskManagementSystem.dto;

import effectiveMobile.com.taskManagementSystem.domain.enums.Priority;
import effectiveMobile.com.taskManagementSystem.domain.enums.Status;

import java.util.List;

public record TaskDto(
        Long id,
        String title,
        String description,
        Status status,
        Priority priority,
        Long author,
        Long executor,
        List<Long> comments) {
}