package root.services;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import root.model.Category;
import root.model.News;
import root.repositories.NewsRepository;

import java.util.Date;
import java.util.List;

@Service
public class NewsService {

    NewsRepository newsRepo;
    CategoryService categoryService;

    public NewsService(NewsRepository newsRepo, CategoryService categoryService) {
        this.newsRepo = newsRepo;
        this.categoryService = categoryService;
    }

    /**
     * Метод достаёт из базы все новости.
     *
     * @return List новостей.
     */
    public List<News> findAllNews() {
        return newsRepo.findAll();
    }

    /**
     * Поиск новости в базе по id.
     *
     * @param id id новости.
     * @return объект News.
     */
    public News findById(int id) {
        return newsRepo.findById(id).orElse(null);
    }

    /**
     * Метод достаёт из базы все новости с соответствующей категорией.
     *
     * @param id id категории.
     * @return List новостей.
     */
    public List<News> findByCategoryId(int id) {
        return newsRepo.findByCategoryId(id);
    }

    /**
     * Метод достаёт из базы все новости с заголовком, частично или полностью, совпадающим с запросом.
     *
     * @param query запрос.
     * @return List новостей.
     */
    public List<News> findByTitle(String query) {
        return newsRepo.findAllByTitleContaining(query);
    }

    /**
     * Метод достаёт из базы все новости с текстом, частично или полностью, совпадающим с запросом.
     *
     * @param query запрос.
     * @return List новостей.
     */
    public List<News> findByText(String query) {
        return newsRepo.findAllByTextContaining(query);
    }

    /**
     * Метод добавляет в базу новую новость или обновляет существующую.
     *
     * @param newsForm     новость.
     * @param categoryForm категория.
     */
    public void addNews(News newsForm, Category categoryForm) {
        Category category = categoryService.findOrAdd(categoryForm.getName());
        News news = new News();
        if (newsForm.getId() != null) {
            try {
                news = newsRepo.findById(newsForm.getId())
                        .orElseThrow(() -> new NotFoundException("Новость с id = " + newsForm.getId() + " не найдена."));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            news.setText(newsForm.getText());
            news.setTitle(newsForm.getTitle());
        } else {
            news = newsForm;
        }
        news.setPublicationDate(new Date());
        news.setCategory(category);
        newsRepo.save(news);
    }

    /**
     * Метод удаляет новость из базы по id.
     *
     * @param id id новости.
     */
    public void deleteNews(int id) {
        newsRepo.deleteById(id);
    }
}
