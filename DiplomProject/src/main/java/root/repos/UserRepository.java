package root.repos;

import org.springframework.data.repository.CrudRepository;
import root.model.Users;

public interface UserRepository extends CrudRepository<Users, Long> {

}
