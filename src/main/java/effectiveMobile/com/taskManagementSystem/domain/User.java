package effectiveMobile.com.taskManagementSystem.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Пользователь
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "uniqueEmail", columnNames = "email")})
@SequenceGenerator(name = "default_generator", sequenceName = "users_seq", allocationSize = 1)
public class User extends GenericModel {

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "role_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USERS_ROLES"))
	private Role role;

	@OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Task> authorTaskList;

	@OneToMany(mappedBy = "executor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Task> executorTaskList;

	@Column(name = "change_password_token")
	private String changePasswordToken;
}
