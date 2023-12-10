package effectiveMobile.com.taskManagementSystem.mappers;

import effectiveMobile.com.taskManagementSystem.domain.GenericModel;
import effectiveMobile.com.taskManagementSystem.domain.User;
import effectiveMobile.com.taskManagementSystem.dto.UserDto;
import effectiveMobile.com.taskManagementSystem.repositories.CommentRepository;
import effectiveMobile.com.taskManagementSystem.repositories.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper extends GenericMapper<User, UserDto> {

	private final TaskRepository taskRepository;
	private final CommentRepository commentRepository;

	public UserMapper(Class<User> entityClass,
					  Class<UserDto> dtoClass,
					  ModelMapper modelMapper,
					  TaskRepository taskRepository,
					  CommentRepository commentRepository) {
		super(entityClass, dtoClass, modelMapper);
		this.taskRepository = taskRepository;
		this.commentRepository = commentRepository;
	}

	@Override
	protected void setupMapper() {

		modelMapper.createTypeMap(User.class, UserDto.class)
				.addMappings(m -> m.skip(UserDto::setAuthorTaskList))
				.addMappings(m -> m.skip(UserDto::setExecutorTaskList))
				.setPostConverter(toDTOConverter());

		modelMapper.createTypeMap(UserDto.class, User.class)
				.addMappings(m -> m.skip(User::setAuthorTaskList))
				.addMappings(m -> m.skip(User::setExecutorTaskList))
				.setPostConverter(toEntityConverter());
	}

	@Override
	protected void mapSpecificFields(UserDto source, User destination) {
		if (!Objects.isNull(source.getAuthorTaskList())) {
			destination.setAuthorTaskList(taskRepository.findAllById(source.getAuthorTaskList()));
		} else {
			destination.setAuthorTaskList(Collections.emptyList());
		}

		if (!Objects.isNull(source.getExecutorTaskList())) {
			destination.setExecutorTaskList(taskRepository.findAllById(source.getExecutorTaskList()));
		} else {
			destination.setExecutorTaskList(Collections.emptyList());
		}
	}

	@Override
	protected void mapSpecificFields(User source, UserDto destination) {
		destination.setAuthorTaskList(getAuthorIds(source));
		destination.setExecutorTaskList(getExecutorIds(source));
	}

	protected List<Long> getAuthorIds(User entity) {
		return Objects.isNull(entity) || Objects.isNull(entity.getAuthorTaskList())
				? null
				: entity.getAuthorTaskList().stream()
				.map(GenericModel::getId)
				.collect(Collectors.toList());
	}

	protected List<Long> getExecutorIds(User entity) {
		return Objects.isNull(entity) || Objects.isNull(entity.getExecutorTaskList())
				? null
				: entity.getExecutorTaskList().stream()
				.map(GenericModel::getId)
				.collect(Collectors.toList());
	}
}
