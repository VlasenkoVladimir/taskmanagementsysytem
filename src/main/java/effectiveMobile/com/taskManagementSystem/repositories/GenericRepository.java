package effectiveMobile.com.taskManagementSystem.repositories;

import effectiveMobile.com.taskManagementSystem.domain.GenericModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Абстрактный репозиторий
 *
 * @param <T> - Сущность с которой работает репозиторий
 */

@NoRepositoryBean
public interface GenericRepository<T extends GenericModel> extends JpaRepository<T, Long> {

}
