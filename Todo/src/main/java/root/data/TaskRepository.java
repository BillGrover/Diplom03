package root.data;

import org.springframework.data.repository.CrudRepository;
import root.domain.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
