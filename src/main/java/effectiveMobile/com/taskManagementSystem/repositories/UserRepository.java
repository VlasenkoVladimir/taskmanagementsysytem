package effectiveMobile.com.taskManagementSystem.repositories;

import effectiveMobile.com.taskManagementSystem.domain.User;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий пользователей
 * расширяющий абстрактный репозиторий параметризованный User
 */

@Repository
public interface UserRepository extends GenericRepository<User> {

    User findUserByEmail(String email);

    User findUserByChangePasswordToken(String uuid);
}
