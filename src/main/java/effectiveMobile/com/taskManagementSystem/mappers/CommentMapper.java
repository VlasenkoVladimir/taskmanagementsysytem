package effectiveMobile.com.taskManagementSystem.mappers;

import effectiveMobile.com.taskManagementSystem.domain.Comment;
import effectiveMobile.com.taskManagementSystem.dto.CommentDto;
import effectiveMobile.com.taskManagementSystem.repositories.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

@Component
public class CommentMapper extends GenericMapper<Comment, CommentDto> {

	private final TaskRepository taskRepository;

	public CommentMapper(Class<Comment> entityClass,
						 Class<CommentDto> dtoClass,
						 ModelMapper modelMapper,
						 TaskRepository taskRepository) {
		super(entityClass, dtoClass, modelMapper);
		this.taskRepository = taskRepository;
	}

	@Override
	protected void setupMapper() {

		modelMapper.createTypeMap(Comment.class, CommentDto.class)
				.addMappings(m -> m.skip(CommentDto::setTask))
				.setPostConverter(toDTOConverter());

		modelMapper.createTypeMap(CommentDto.class, Comment.class)
				.addMappings(m -> m.skip(Comment::setTask))
				.setPostConverter(toEntityConverter());
	}

	@Override
	protected void mapSpecificFields(CommentDto source, Comment destination) {
		destination.setTask(taskRepository.findById(source.getTask())
				.orElseThrow(() -> new NotFoundException("Данных по заданному id: " + source.getTask() + " не найдено")));
	}

	@Override
	protected void mapSpecificFields(Comment source, CommentDto destination) {
		destination.setTask(source.getTask().getId());
	}
}
