package root.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import root.dtoResponses.PostResponse;
import root.model.Post;
import root.model.enums.ModerationStatus;
import root.repositories.PostRepository;
import root.repositories.UserRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PostService {

    PostRepository postRepo;
    UserRepository userRepo;

    public PostService(PostRepository postRepo, UserRepository userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    /**
     * Метод получения постов со всей сопутствующей информацией для главной страницы и подразделов "Новые", "Самые обсуждаемые", "Лучшие" и "Старые".
     * Метод выводит посты, отсортированные в соответствии с параметром mode.
     *
     * @param offsetStr Offset for pagination.
     * @param limitStr  Limit  for pagination.
     * @param mode      recent/popular/best/early
     * @return ResponseEntity
     */
    public ResponseEntity<PostResponse> getPosts(String offsetStr, String limitStr, String mode) {
        int offset, limit;
        try {
            offset = Integer.parseInt(offsetStr);
            limit = Integer.parseInt(limitStr);
        } catch (Exception e) {
            System.out.println("Ошибка параметров (offset or limit) в GET-запросе api/post\n" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pageable pagination = PageRequest.of(offset, limit);
        Page<Post> posts;
        switch (mode) {
            case ("recent"):    //сначала новые
                posts = postRepo.findRecent(pagination);
                break;
            case ("popular"):   //по убыванию комментариев (comments.size)
                posts = postRepo.findPopular(pagination);
                break;
            case ("best"):      //по убыванию лайков (сумма значений листа votes)
                posts = postRepo.findBest(pagination);
                break;
            case ("early"):     //сначала старые
                posts = postRepo.findEarly(pagination);
                break;
            default:            //по id
                posts = postRepo.findAllByIsActiveAndModerationStatusAndTimeBeforeOrderById(
                        (byte) 1, ModerationStatus.ACCEPTED, new Date(), pagination);
        }
        return new ResponseEntity<>(new PostResponse(posts), HttpStatus.OK);
    }

    /**
     * Метод возвращает посты, соответствующие поисковому запросу - строке query.
     * В случае, если запрос пустой, метод выводит все посты.
     *
     * @param offsetStr Offset for pagination.
     * @param limitStr  Limit  for pagination.
     * @param query     Query for search
     * @return ResponseEntity
     */
    public ResponseEntity<PostResponse> getByQuery(String offsetStr, String limitStr, String query) {
        int offset, limit;
        try {
            offset = Integer.parseInt(offsetStr);
            limit = Integer.parseInt(limitStr);
        } catch (Exception e) {
            System.out.println("Ошибка параметров (offset or limit) в GET-запросе api/post/search\n" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Page<Post> posts = postRepo.findAllByIsActiveAndModerationStatusAndTimeBeforeOrderById(
                (byte) 1, ModerationStatus.ACCEPTED, new Date(), PageRequest.of(offset, limit));
        return new ResponseEntity<>(new PostResponse(posts), HttpStatus.OK);
    }


    /**
     * Выводит посты за указанную дату, переданную в запросе в параметре date.
     *
     * @param offsetStr Offset for pagination.
     * @param limitStr  Limit  for pagination.
     * @param dateStr   The day for search.
     * @return ResponseEntity
     */
    public ResponseEntity<PostResponse> getByDate(String offsetStr, String limitStr, String dateStr) {
        Page<Post> posts;
        try {
            int offset = Integer.parseInt(offsetStr);
            int limit = Integer.parseInt(limitStr);
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dayStart = df1.parse(dateStr);
            df1 = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-SSS");
            Date dayEnd = df1.parse(dateStr + "-23-59-59-999");

            posts = postRepo.findAllByIsActiveAndModerationStatusAndTimeBeforeAndTimeBetweenOrderByTimeDesc(
                    (byte) 1, ModerationStatus.ACCEPTED, new Date(), dayStart, dayEnd, PageRequest.of(offset, limit));

        } catch (Exception e) {
            System.out.println("Ошибка параметров (offset, date or limit) в GET-запросе api/post/byDate\n" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new PostResponse(posts), HttpStatus.OK);
    }

    /**
     * Метод выводит список постов, привязанных к тэгу, который был передан методу в качестве параметра tag.
     *
     * @param offsetStr Offset for pagination.
     * @param limitStr  Limit  for pagination.
     * @param tag       Tag name for search.
     * @return ResponseEntity.
     */
    public ResponseEntity<PostResponse> getByTag(String offsetStr, String limitStr, String tag) {
        Page<Post> posts;
        try {
            int offset = Integer.parseInt(offsetStr);
            int limit = Integer.parseInt(limitStr);
            posts = postRepo.findbyTag(tag, PageRequest.of(offset, limit));
        } catch (Exception e) {
            System.out.println("Ошибка параметров (offset, tag or limit) в GET-запросе api/post/byTag\n" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new PostResponse(posts), HttpStatus.OK);
    }

    /**
     * Метод выводит все посты, которые требуют модерационных действий (которые нужно утвердить или отклонить)
     * или над которыми мною были совершены модерационные действия: которые я отклонил или утвердил.
     *
     * @param offsetStr Offset for pagination.
     * @param limitStr  Limit  for pagination.
     * @param status    Moderation status.
     * @return ResponseEntity.
     */
    public ResponseEntity<PostResponse> getModeration(String offsetStr, String limitStr, String status) {
        int offset, limit;
        try {
            offset = Integer.parseInt(offsetStr);
            limit = Integer.parseInt(limitStr);
            ModerationStatusChecker.ok(status);
        } catch (Exception e) {
            System.out.println("Ошибка параметров (status, offset or limit) в GET-запросе api/post/moderation\n" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int currentUserId = userRepo.findByName(userDetails.getUsername()).getId();

        Page<Post> posts;
        if (status.toUpperCase().equals("NEW"))
            posts = postRepo.findbyStatus(status.toUpperCase(), PageRequest.of(offset, limit));
        else
            posts = postRepo.findbyStatus(status.toUpperCase(), currentUserId, PageRequest.of(offset, limit));

        return new ResponseEntity<>(new PostResponse(posts), HttpStatus.OK);
    }
}
