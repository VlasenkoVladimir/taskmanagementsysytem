package effectiveMobile.com.taskManagementSystem.dto;

import effectiveMobile.com.taskManagementSystem.domain.enums.Priority;
import effectiveMobile.com.taskManagementSystem.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskDto extends GenericDto {

	private String title;
	private String description;
	private Status status;
	private Priority priority;
	private Long author;
	private Long executor;
	private List<Long> comments;
}
