package root.services;

import org.springframework.stereotype.Service;
import root.model.Category;
import root.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    /**
     * Поиск всех категорий в базе данных.
     *
     * @return List всех категорий.
     */
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    /**
     * Метод ищет в базе категорию. Если не находит такой, добавляет новую.
     * @param categoryName искомая категория.
     * @return категория из базы данных.
     */
    public Category findOrAdd(String categoryName) {
        if (categoryRepo.existsByName(categoryName))
            return categoryRepo.findByName(categoryName);
        else {
            Category newCategory = new Category();
            newCategory.setName(categoryName);
            categoryRepo.save(newCategory);
            return newCategory;
        }
    }
}
