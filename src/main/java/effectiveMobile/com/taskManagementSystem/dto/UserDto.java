package effectiveMobile.com.taskManagementSystem.dto;

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
public class UserDto extends GenericDto {

	private String email;
	private String password;
	private RoleDto roleDto;
	private List<Long> authorTaskList;
	private List<Long> executorTaskList;
	private String changePasswordToken;
}
