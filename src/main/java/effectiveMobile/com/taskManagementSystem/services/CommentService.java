package effectiveMobile.com.taskManagementSystem.services;

import effectiveMobile.com.taskManagementSystem.domain.Comment;
import effectiveMobile.com.taskManagementSystem.dto.CommentDto;
import effectiveMobile.com.taskManagementSystem.mappers.GenericMapper;
import effectiveMobile.com.taskManagementSystem.repositories.CommentRepository;
import effectiveMobile.com.taskManagementSystem.repositories.GenericRepository;
import effectiveMobile.com.taskManagementSystem.repositories.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CommentService extends GenericService<Comment, CommentDto> {

	private final TaskRepository taskRepository;

	public CommentService(GenericRepository<Comment> repository,
						  GenericMapper<Comment, CommentDto> mapper,
						  TaskRepository taskRepository) {
		super(repository, mapper);
		this.taskRepository = taskRepository;
	}

	public CommentDto create(final CommentDto commentDto) {
		commentDto.setTask(taskRepository.findById(commentDto.getId()).orElseThrow().getId());

		return mapper.toDTO(repository.save(mapper.toEntity(commentDto)));
	}

	public List<CommentDto> getCommentsByTaskId(Long taskId) {

		return mapper.toDTOs(((CommentRepository) repository).getCommentsByTaskId(taskId));
	}

	public Page<CommentDto> getCommentsByTaskId(Long taskId, Pageable pageable) {
		List<Comment> commentsList = ((CommentRepository) repository).getCommentsByTaskId(taskId, pageable).getContent();

		return new PageImpl<>(mapper.toDTOs(commentsList));
	}
}
