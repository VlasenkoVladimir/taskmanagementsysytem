package effectiveMobile.com.taskManagementSystem.domain;

import effectiveMobile.com.taskManagementSystem.domain.enums.Priority;
import effectiveMobile.com.taskManagementSystem.domain.enums.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Задача
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
@SequenceGenerator(name = "default_generator", sequenceName = "tasks_sequence", allocationSize = 1)
public class Task extends GenericModel {

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "status", nullable = false)
	@Enumerated
	private Status status;

	@Column(name = "priority", nullable = false)
	@Enumerated
	private Priority priority;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_AUTHOR"))
	private User author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_EXECUTOR"))
	private User executor;

	@OneToMany(mappedBy = "task", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Comment> comments;
}
