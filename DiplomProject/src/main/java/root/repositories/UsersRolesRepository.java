package root.repositories;

import org.springframework.data.repository.CrudRepository;
import root.model.Role2User;

public interface UsersRolesRepository extends CrudRepository <Role2User, Long> {


}
