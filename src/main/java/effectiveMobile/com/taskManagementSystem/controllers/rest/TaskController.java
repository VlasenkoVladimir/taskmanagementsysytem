package effectiveMobile.com.taskManagementSystem.controllers.rest;

import effectiveMobile.com.taskManagementSystem.domain.Task;
import effectiveMobile.com.taskManagementSystem.dto.TaskDto;
import effectiveMobile.com.taskManagementSystem.services.GenericService;
import effectiveMobile.com.taskManagementSystem.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/api/rest/tasks")
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Задачи", description = "Контроллер для работы с задачами")
public class TaskController extends GenericController<Task, TaskDto> {

	public TaskController(GenericService<Task, TaskDto> genericService) {
		super(genericService);
	}

	@Operation(description = "Получить список всех задач постранично")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Записи получены",
					content = {@Content(
							mediaType = "application/json",
							schema = @Schema(implementation = TaskDto.class))
					}),
			@ApiResponse(responseCode = "204", description = "Записи не найдены", content = {@Content()})
	})
	@RequestMapping(value = "/listAllTasksPaginated", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<TaskDto> listAllTasksPaginated(@RequestParam(value = "pageable") Pageable pageable) {

		return ResponseEntity.status(OK).body((TaskService) service).getBody().listAllTasksPaginated(pageable);
	}


	@Operation(description = "Назначить исполнителя задачи")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Исполнитель назначен")})
	@RequestMapping(value = "/listAllTasksPaginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> setExecutor(@RequestParam(value = "taskId") Long taskId,
												  @RequestParam(value = "executorId") Long executorId) {
		((TaskService) service).setExecutor(taskId, executorId);

		return new ResponseEntity<>(OK);
	}

	public List<TaskDto> getTasksByAuthor(Long authorId) {}
}
