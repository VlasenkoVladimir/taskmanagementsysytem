package effectiveMobile.com.taskManagementSystem.services;

import effectiveMobile.com.taskManagementSystem.domain.Task;
import effectiveMobile.com.taskManagementSystem.domain.User;
import effectiveMobile.com.taskManagementSystem.domain.enums.Priority;
import effectiveMobile.com.taskManagementSystem.domain.enums.Status;
import effectiveMobile.com.taskManagementSystem.dto.TaskDto;
import effectiveMobile.com.taskManagementSystem.mappers.TaskMapper;
import effectiveMobile.com.taskManagementSystem.repositories.TaskRepository;
import effectiveMobile.com.taskManagementSystem.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Tsk service class
 */
@Slf4j
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = TaskMapper.INSTANCE;
        this.userRepository = userRepository;
    }

    /**
     * Creating new task
     *
     * @param newTaskDto DTO with new task data
     * @return TaskDto if new task saved correctly
     */
    public TaskDto create(final TaskDto newTaskDto) {
        log.info("Call create new task {}", newTaskDto.title());

        //todo verify incoming data

        return taskMapper.toDto(taskRepository.save(taskMapper.toTask(newTaskDto)));
    }

    public TaskDto getById(final long id) {
        log.info("Call getById with id is {}", id);

        //todo verify incoming data

        return taskMapper.toDto(taskRepository.getReferenceById(id));
    }

    /**
     * Get Page with list of tasks
     *
     * @param pageable Pageable
     * @return Page with all TaskDto
     */
    public PageImpl<TaskDto> getAllPaginated(final Pageable pageable) {
        log.info("Call listAllTasksPaginated with pageable {}", pageable);

        Page<Task> tasksPaginated = taskRepository.findAll(pageable);
        List<TaskDto> result = tasksPaginated.getContent().stream().map(taskMapper::toDto).toList();

        return new PageImpl<>(result, pageable, tasksPaginated.getTotalElements());
    }


    /**
     * Get all tasks where author id is authorId
     *
     * @param authorId long author id
     * @return List<TaskDto> of all tasks by this author
     */
    public List<TaskDto> getTasksByAuthorId(final Long authorId) {
        log.info("Call getTasksByAuthor with authorId is {}", authorId);

        //todo verify incoming data

        return taskRepository.getTasksByAuthorId(authorId).stream().map(taskMapper::toDto).toList();
    }

    /**
     * Get all Tasks for author
     *
     * @param authorId long author id
     * @param pageable Pageable
     * @return Page with all Tasks where author id is authorId
     */
    public PageImpl<TaskDto> getTasksByAuthorIdPaginated(final Long authorId, final Pageable pageable) {
        log.info("Call getTasksByAuthor with authorId is {} and pageable is {}", authorId, pageable);

        //todo verify incoming data

        Page<Task> taskPage = taskRepository.getTasksByAuthorId(authorId, pageable);
        List<TaskDto> result = taskPage.getContent().stream().map(taskMapper::toDto).toList();

        return new PageImpl<>(result, pageable, taskPage.getTotalElements());
    }

    /**
     * Get all tasks by executor
     *
     * @param executorId long executor id
     * @return List<TaskDto> of all tasks by this executor
     */
    public List<TaskDto> getTasksByExecutorId(final Long executorId) {
        log.info("Call getTasksByExecutor with executorId is {}", executorId);

        //todo verify incoming data

        return taskRepository.getTasksByExecutorId(executorId).stream().map(taskMapper::toDto).toList();
    }

    /**
     * Get all tasks of executor
     *
     * @param executorId long executor id
     * @param pageable   Pageable
     * @return Page with all Tasks where executor id is executorId
     */
    public Page<TaskDto> getTasksByExecutorIdPaginated(final Long executorId, final Pageable pageable) {
        log.info("Call getTasksByExecutor with executorId is {} and pageable is {}", executorId, pageable);

        //todo verify incoming data

        Page<Task> taskPage = taskRepository.getTasksByExecutorId(executorId, pageable);
        List<TaskDto> result = taskPage.getContent().stream().map(taskMapper::toDto).toList();

        return new PageImpl<>(result, pageable, taskPage.getTotalElements());
    }

    /**
     * Set Task executor
     *
     * @param taskId     long Task id
     * @param executorId long executor id
     * @return TaskDto if change correctly
     */
    public TaskDto setExecutor(final Long taskId, final Long executorId) {
        log.info("Call setExecutor with task id is {} and executor id is {}", taskId, executorId);

        // todo verify incoming data
        // todo move check role to controller
//        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
//                .anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN"));

        final Task taskForSetExecutor = taskRepository.findById(taskId).orElseThrow();
        final User executor = userRepository.findById(executorId).orElseThrow();
        taskForSetExecutor.setExecutor(executor);

        return taskMapper.toDto(taskRepository.save(taskForSetExecutor));
    }

    /**
     * Change task status
     *
     * @param newStatus new Task Status
     * @param taskId    long Task id
     * @return TaskDto if executed correctly
     */
    public TaskDto changeStatus(final Status newStatus, final Long taskId) {
        log.info("Call changeStatus with Status is {} and task id is {}", newStatus, taskId);

        //todo verify incoming data

        final Task taskForChangeStatus = taskRepository.findById(taskId).orElseThrow();
        taskForChangeStatus.setStatus(newStatus);

        return taskMapper.toDto(taskRepository.save(taskForChangeStatus));
    }

    /**
     * Change Priority for existed Task
     *
     * @param newPriority new Task Priority
     * @param taskId      long Task id
     * @return TaskDto if executed correctly
     */
    public TaskDto changePriority(final Priority newPriority, final Long taskId) {
        log.info("Call changePriority with Priority is {} and task id is {}", newPriority, taskId);

        //todo verify incoming data

        final Task taskForChangePriority = taskRepository.findById(taskId).orElseThrow();
        taskForChangePriority.setPriority(newPriority);


        return taskMapper.toDto(taskRepository.save(taskForChangePriority));
    }

    /**
     * Update existed task
     *
     * @param updatedTask Task for update
     * @return TaskDto of updated Task
     */
    public TaskDto update(final TaskDto updatedTask) {
        log.info("Call update with TaskDto is {}", updatedTask);

        //todo verify incoming data

        return taskMapper.toDto(taskRepository.save(taskMapper.toTask(updatedTask)));
    }

    /**
     * Delete Task by id
     *
     * @param id long Task id
     * @return true if correctly executed
     */
    public boolean delete(final Long id) {
        log.info("Call delete with task id is {}", id);

        //todo verify incoming data

        taskRepository.deleteById(id);

        return true;
    }
}