package effectiveMobile.com.taskManagementSystem.repositories;

import effectiveMobile.com.taskManagementSystem.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий комментариев
 * расширяющий абстрактный репозиторий параметризованный Comment
 */

@Repository
public interface CommentRepository extends GenericRepository<Comment> {

	Page<Comment> getCommentsByTaskId(Long taskId, Pageable pageRequest);

	List<Comment> getCommentsByTaskId(Long taskId);
}
