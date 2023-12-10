package effectiveMobile.com.taskManagementSystem.controllers.rest;


import effectiveMobile.com.taskManagementSystem.config.jwt.JWTTokenUtil;
import effectiveMobile.com.taskManagementSystem.domain.User;
import effectiveMobile.com.taskManagementSystem.dto.LoginDto;
import effectiveMobile.com.taskManagementSystem.dto.UserDto;
import effectiveMobile.com.taskManagementSystem.services.GenericService;
import effectiveMobile.com.taskManagementSystem.services.UserService;
import effectiveMobile.com.taskManagementSystem.services.userdetails.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/rest/users")
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Пользователи", description = "Контроллер для работы с пользователями")
public class UserController extends GenericController<User, UserDto> {

	private final CustomUserDetailsService customUserDetailsService;
	private final JWTTokenUtil jwtTokenUtil;
	private final UserService userService;

	public UserController(GenericService<User, UserDto> genericService,
						  CustomUserDetailsService customUserDetailsService,
						  JWTTokenUtil jwtTokenUtil,
						  UserService userService) {
		super(genericService);
		this.customUserDetailsService = customUserDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userService = userService;
	}

	@PostMapping("/auth")
	public ResponseEntity<?> auth(@RequestBody LoginDto loginDto) {
		Map<String, Object> response = new HashMap<>();
		UserDetails foundUser = customUserDetailsService.loadUserByUsername(loginDto.getEmail());
		log.info("foundUser: {}", foundUser);
		if (!userService.checkPassword(loginDto.getPassword(), foundUser)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ошибка авторизации! \n Неверный пароль...");
		}
		String token = jwtTokenUtil.generateToken(foundUser);
		response.put("token", token);
		response.put("username", foundUser.getUsername());
		response.put("role", foundUser.getAuthorities());
		return ResponseEntity.ok().body(response);
	}
}
