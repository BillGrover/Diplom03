package root.repositories;

import org.springframework.data.repository.CrudRepository;
import root.model.Tag;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Long> {

    List<Tag> findAll();

    List<Tag> findAllByTitleStartingWith(String s);

    int countAllByTitle(String s);

    Tag findByTitle(String s);
}
