package root.repos;

import org.springframework.data.repository.CrudRepository;
import root.model.PostVotes;

public interface VotesRepository extends CrudRepository<PostVotes, Long> {
}
