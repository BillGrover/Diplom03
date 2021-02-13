package root.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import root.model.Tag2Post;

@Repository
public interface Tag2PostRepository extends PagingAndSortingRepository <Tag2Post, Long> {
}
