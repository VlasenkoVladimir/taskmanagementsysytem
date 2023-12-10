package effectiveMobile.com.taskManagementSystem.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Комментарий
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comments")
@SequenceGenerator(name = "default_generator", sequenceName = "comments_sequence", allocationSize = 1)
public class Comment extends GenericModel {

	@Column(name = "description", nullable = false)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id", nullable = false, foreignKey = @ForeignKey(name = "FK_COMMENT_TASK"))
	private Task task;
}
