package root.repositories;

import org.springframework.data.repository.CrudRepository;
import root.model.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findAll();

    boolean existsByName(String categoryName);

    Category findByName(String categoryName);
}
