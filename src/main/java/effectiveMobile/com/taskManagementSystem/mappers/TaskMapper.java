package effectiveMobile.com.taskManagementSystem.mappers;

import effectiveMobile.com.taskManagementSystem.domain.Task;
import effectiveMobile.com.taskManagementSystem.dto.TaskDto;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * Mapper for Task
 */
@Component
public interface TaskMapper{
	TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

	TaskDto toDto(Task task);

	Task toTask(TaskDto taskDto);
}