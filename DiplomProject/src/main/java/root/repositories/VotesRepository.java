package root.repositories;

import org.springframework.data.repository.CrudRepository;
import root.model.PostVote;

public interface VotesRepository extends CrudRepository<PostVote, Long> {
}
