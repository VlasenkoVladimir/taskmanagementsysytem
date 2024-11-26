package effectiveMobile.com.taskManagementSystem.repositories;

import effectiveMobile.com.taskManagementSystem.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий задач
 * расширяющий абстрактный репозиторий параметризованный Task
 */

@Repository
public interface TaskRepository extends GenericRepository<Task> {

	Page<Task> getTasksByAuthorId(Long authorId, Pageable pageRequest);
	List<Task> getTasksByAuthorId(Long authorId);

	Page<Task> getTasksByExecutorId(Long executorId, Pageable pageRequest);
	List<Task> getTasksByExecutorId(Long executorId);
}