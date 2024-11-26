package effectiveMobile.com.taskManagementSystem.dto;

public record CommentDto(
		Long id,
		String description,
		Long task) {
}