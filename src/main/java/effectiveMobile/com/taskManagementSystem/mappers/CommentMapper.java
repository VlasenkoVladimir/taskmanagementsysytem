package effectiveMobile.com.taskManagementSystem.mappers;

import effectiveMobile.com.taskManagementSystem.domain.Comment;
import effectiveMobile.com.taskManagementSystem.dto.CommentDto;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * Mapper for Comment
 */
@Component
public interface CommentMapper {
	CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

	CommentDto toDto(Comment comment);

	Comment toComment(CommentDto commentDto);
}