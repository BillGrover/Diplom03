package root.repositories;

import org.springframework.data.repository.CrudRepository;
import root.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
    User findByName (String name);
    Boolean existsByEmail(String email);
}
