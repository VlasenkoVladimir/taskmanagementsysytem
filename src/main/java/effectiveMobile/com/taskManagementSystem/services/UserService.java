package effectiveMobile.com.taskManagementSystem.services;

import effectiveMobile.com.taskManagementSystem.domain.User;
import effectiveMobile.com.taskManagementSystem.dto.RoleDto;
import effectiveMobile.com.taskManagementSystem.dto.UserDto;
import effectiveMobile.com.taskManagementSystem.mappers.GenericMapper;
import effectiveMobile.com.taskManagementSystem.repositories.GenericRepository;
import effectiveMobile.com.taskManagementSystem.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class UserService extends GenericService<User, UserDto> {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserService(GenericRepository<User> repository,
					   GenericMapper<User, UserDto> mapper,
					   BCryptPasswordEncoder bCryptPasswordEncoder) {
		super(repository, mapper);
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDto create(UserDto userDto) {
		if (Objects.isNull(userDto.getRoleDto())) {
			RoleDto roleDto = new RoleDto();
			roleDto.setId(1L);
			userDto.setRoleDto(roleDto);
		}
		userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		return mapper.toDTO(repository.save(mapper.toEntity(userDto)));
	}

	public UserDto createAdmin(UserDto newObject) {
		RoleDto roleDto = new RoleDto();
		roleDto.setId(0L);
		newObject.setRoleDto(roleDto);
		return create(newObject);
	}

	public UserDto getUserByEmail(final String email) {
		return mapper.toDTO(((UserRepository) repository).findUserByEmail(email));
	}

	public boolean checkPassword(String password, UserDetails foundUser) {
		return bCryptPasswordEncoder.matches(password, foundUser.getPassword());
	}

	public void changePassword(String uuid, String password) {
		UserDto userDto = mapper.toDTO(((UserRepository) repository).findUserByChangePasswordToken(uuid));
		userDto.setChangePasswordToken(null);
		userDto.setPassword(bCryptPasswordEncoder.encode(password));
		update(userDto);
	}
}
