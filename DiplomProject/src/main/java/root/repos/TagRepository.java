package root.repos;

import org.springframework.data.repository.CrudRepository;
import root.model.Tags;

public interface TagRepository extends CrudRepository<Tags, Long> {
}
