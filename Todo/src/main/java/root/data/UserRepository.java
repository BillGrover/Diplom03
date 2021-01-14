package root.data;

import org.springframework.data.repository.CrudRepository;
import root.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
