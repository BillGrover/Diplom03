package root.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import root.model.Category;
import root.model.News;
import root.services.CategoryService;
import root.services.NewsService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping
public class NewsController {

    NewsService newsService;
    CategoryService categoryService;

    public NewsController(NewsService newsService, CategoryService categoryService) {
        this.newsService = newsService;
        this.categoryService = categoryService;
    }

    @ModelAttribute(name = "categoryList")
    public List<Category> categoryList() {
        return categoryService.findAll();
    }

    @ModelAttribute(name = "newsForm")
    public News newsForm() {
        return new News();
    }

    @ModelAttribute(name = "categoryForm")
    public Category categoryForm() {
        return new Category();
    }

    /**
     * Метод возвращает все новости на главной странице.
     *
     * @param model модель.
     * @return news.html (главная страница).
     */
    @GetMapping("/home")
    public String showAllNews(Model model) {
        model.addAttribute("newsListAttr", newsService.findAllNews());
        return "news";
    }

    /**
     * Метод возвращает страницу с выбранной новостью.
     *
     * @param id    id новости.
     * @param model модель.
     * @return oneNews.html (страница с запрошенной новостью).
     */
    @GetMapping("/news/{id}")
    public String showAllNews(
            @PathVariable int id,
            Model model) {
        if (newsService.findById(id) == null)
            return "notFound404";
        model.addAttribute("oneNews", newsService.findById(id));
        return "oneNews";
    }

    /**
     * Метод возвращает все новости выбранной категории на гланой странице.
     *
     * @param categoryForm атрибут Category.
     * @param model        модель.
     * @return news.html (главная страница с новостями, относящимися к выбранной категории).
     */
    @PostMapping("/news/byCategory")
    public String newsByCategory(Category categoryForm, Model model) {
        int catId = categoryForm.getId();
        model.addAttribute(
                "newsListAttr",
                catId == 1 ? newsService.findAllNews() : newsService.findByCategoryId(catId));
        return "news";
    }

    /**
     * Метод ищет совпадение в заголовках новостей в соответствии с запросом.
     *
     * @param newsForm Запрос приходит в поле title параметра newsForm.
     * @param model    модель.
     * @return news.html (главная страница с новостями, в заголовках которых содержится запрос).
     */
    @PostMapping("/news/byTitle")
    public String newsByTitle(News newsForm, Model model) {
        String query = newsForm.getTitle();
        model.addAttribute(
                "newsListAttr",
                query == null ? newsService.findAllNews() : newsService.findByTitle(query));
        return "news";
    }

    /**
     * Метод ищет совпадение в текстах новостей в соответствии с запросом.
     *
     * @param newsForm Запрос приходит в поле text параметра newsForm.
     * @param model    модель.
     * @return news.html (главная страница с новостями, в текстах которых содержится запрос).
     */
    @PostMapping("/news/byText")
    public String newsByText(News newsForm, Model model) {
        String query = newsForm.getText();
        model.addAttribute(
                "newsListAttr",
                query == null ? newsService.findAllNews() : newsService.findByText(query));
        return "news";
    }

    /**
     * Метод возвращает страницу редактирования новости.
     *
     * @return newsForm.html (страница редактирования новости).
     */
    @GetMapping("/news/new")
    public String createNews() {
        return "newsForm";
    }

    /**
     * Метод записывает базу данных новую новость, предварительно проверяя поля на заполненность.
     *
     * @param model          модель.
     * @param newsForm       атрибут News.
     * @param bindingResult1 валидатор News.
     * @param categoryForm   атрибут Category.
     * @param bindingResult2 валидатор Category.
     * @return редирект на главную страницу.
     */
    @PostMapping("/news/new")
    public String createNews(
            Model model,
            @ModelAttribute("newsForm") @Valid News newsForm,
            BindingResult bindingResult1,
            @ModelAttribute("categoryForm") @Valid Category categoryForm,
            BindingResult bindingResult2
    ) {
        if (bindingResult1.hasErrors() || bindingResult2.hasErrors()) {
            return "newsForm";
        } else {
            newsService.addNews(newsForm, categoryForm);
            return "redirect:/home";
        }
    }

    /**
     * Метод удаляет новость из базы.
     *
     * @param id id новости, которую надо удалить.
     * @return редирект на главную страницу.
     */
    @GetMapping("/news/delete/{id}")
    public String deleteNews(@PathVariable int id) {
        newsService.deleteNews(id);
        return "redirect:/home";
    }

    /**
     * Метод загружает из базы в модель новость, которую необходимо отредактировать.
     *
     * @param id    id нужной новости.
     * @param model модель.
     * @return newsForm.html (страница редактирования новости).
     */
    @GetMapping("/news/edit/{id}")
    public String editNews(@PathVariable int id, Model model) {
        News news = newsService.findById(id);
        model.addAttribute("newsForm", news);
        model.addAttribute("categoryForm", news.getCategory());
        return "newsForm";
    }
}
