package effectiveMobile.com.taskManagementSystem.mappers;

import effectiveMobile.com.taskManagementSystem.domain.GenericModel;
import effectiveMobile.com.taskManagementSystem.domain.Task;
import effectiveMobile.com.taskManagementSystem.dto.TaskDto;
import effectiveMobile.com.taskManagementSystem.repositories.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TaskMapper extends GenericMapper<Task, TaskDto> {

	private final CommentRepository commentRepository;

	public TaskMapper(Class<Task> entityClass,
					  Class<TaskDto> dtoClass,
					  ModelMapper modelMapper,
					  CommentRepository commentRepository) {
		super(entityClass, dtoClass, modelMapper);
		this.commentRepository = commentRepository;
	}

	@Override
	protected void setupMapper() {

		modelMapper.createTypeMap(Task.class, TaskDto.class)
				.addMappings(m -> m.skip(TaskDto::setComments))
				.setPostConverter(toDTOConverter());

		modelMapper.createTypeMap(TaskDto.class, Task.class)
				.addMappings(m -> m.skip(Task::setComments))
				.setPostConverter(toEntityConverter());
	}

	@Override
	protected void mapSpecificFields(TaskDto source, Task destination) {
		if (!Objects.isNull(source.getComments())) {
			destination.setComments(commentRepository.findAllById(source.getComments()));
		} else {
			destination.setComments(Collections.emptyList());
		}
	}

	@Override
	protected void mapSpecificFields(Task source, TaskDto destination) {
		destination.setComments(getCommentIds(source));
	}

	protected List<Long> getCommentIds(Task entity) {
		return Objects.isNull(entity) || Objects.isNull(entity.getComments())
				? null
				: entity.getComments().stream()
				.map(GenericModel::getId)
				.collect(Collectors.toList());
	}
}
