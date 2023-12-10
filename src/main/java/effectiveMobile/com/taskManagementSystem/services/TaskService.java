package effectiveMobile.com.taskManagementSystem.services;

import effectiveMobile.com.taskManagementSystem.domain.Task;
import effectiveMobile.com.taskManagementSystem.domain.User;
import effectiveMobile.com.taskManagementSystem.domain.enums.Status;
import effectiveMobile.com.taskManagementSystem.dto.TaskDto;
import effectiveMobile.com.taskManagementSystem.mappers.GenericMapper;
import effectiveMobile.com.taskManagementSystem.repositories.GenericRepository;
import effectiveMobile.com.taskManagementSystem.repositories.TaskRepository;
import effectiveMobile.com.taskManagementSystem.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static effectiveMobile.com.taskManagementSystem.constants.Errors.REST.ACCESS_ERROR_MESSAGE;

@Slf4j
@Service
public class TaskService extends GenericService<Task, TaskDto> {

	private final UserRepository userRepository;

	public TaskService(GenericRepository<Task> repository,
					   GenericMapper<Task, TaskDto> mapper,
					   UserRepository userRepository) {
		super(repository, mapper);
		this.userRepository = userRepository;
	}

	public TaskDto create(final TaskDto newTask) {
		newTask.setAuthor(userRepository.findUserByEmail(
				SecurityContextHolder.getContext()
						.getAuthentication()
						.getName()).getId());

		return mapper.toDTO(repository.save(mapper.toEntity(newTask)));
	}

	public Page<TaskDto> listAllTasksPaginated(Pageable pageable) {
		Page<Task> tasksPaginated = repository.findAll(pageable);
		List<TaskDto> result = mapper.toDTOs(tasksPaginated.getContent());

		return new PageImpl<>(result, pageable, tasksPaginated.getTotalElements());
	}

	public void setExecutor(Long taskId, Long executorId) {
		final Task taskForSetExecutor = repository.findById(taskId).orElseThrow();
		final User executor = userRepository.findById(executorId).orElseThrow();
		final Long changerId = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication()
				.getName()).getId();

		if (changerId.equals(taskForSetExecutor.getAuthor().getId())) {
			taskForSetExecutor.setExecutor(executor);
			repository.save(taskForSetExecutor);
		} else {
			throw new AccessDeniedException(ACCESS_ERROR_MESSAGE);
		}
	}

	public List<TaskDto> getTasksByAuthor(Long authorId) {

		return mapper.toDTOs(((TaskRepository)repository).getTasksByAuthorId(authorId));
	}

	public Page<TaskDto> getTasksByAuthorPaginated(Long authorId, Pageable pageable) {
		List<Task> tasksList = ((TaskRepository)repository).getTasksByAuthorId(authorId, pageable).getContent();

		return new PageImpl<>(mapper.toDTOs(tasksList));
	}

	public List<TaskDto> getTasksByExecutor (Long executorId) {

		return mapper.toDTOs(((TaskRepository)repository).getTasksByExecutorId(executorId));
	}
	public Page<TaskDto> getTasksByExecutor (Long executorId, Pageable pageable) {
		List<Task> tasksList = ((TaskRepository)repository).getTasksByExecutorId(executorId, pageable).getContent();

		return new PageImpl<>(mapper.toDTOs(tasksList));
	}

	public void changeStatus(Status newStatus, Long taskId) {
		final Task taskForChangeStatus = repository.findById(taskId).orElseThrow();
		final Long taskExecutorId = taskForChangeStatus.getExecutor().getId();
		final Long taskAuthorId = taskForChangeStatus.getAuthor().getId();
		final Long changerId = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication()
				.getName()).getId();

		if (taskExecutorId.equals(changerId) || taskAuthorId.equals(changerId)) {
			taskForChangeStatus.setStatus(newStatus);
			repository.save(taskForChangeStatus);
		} else {
			throw new AccessDeniedException(ACCESS_ERROR_MESSAGE);
		}
	}

	@Override
	public TaskDto update(TaskDto updatedTask) {
		final Long changerId = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication()
				.getName()).getId();
		final Long taskAuthorId = repository.findById(updatedTask.getId()).orElseThrow().getAuthor().getId();

		if (taskAuthorId.equals(changerId)) {
			return super.update(updatedTask);
		} else {
			throw new AccessDeniedException(ACCESS_ERROR_MESSAGE);
		}
	}

	@Override
	public void delete(Long id) {
		final Long taskAuthorId = repository.findById(id).orElseThrow().getAuthor().getId();
		final Long deleterId = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication()
						.getName()).getId();

		if (taskAuthorId.equals(deleterId)) {
			super.delete(id);
		} else {
			throw new AccessDeniedException(ACCESS_ERROR_MESSAGE);
		}
	}
}
