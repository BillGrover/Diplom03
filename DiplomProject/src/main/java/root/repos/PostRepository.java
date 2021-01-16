package root.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import root.model.ModerationStatus;
import root.model.Posts;
import java.util.Date;

public interface PostRepository extends PagingAndSortingRepository<Posts, Long> {

    /********* ДЕФОЛТНЫЙ ПОИСК ПОСТОВ (по Id) *********/
    Page<Posts> findAllByIsActiveAndModerationStatusAndTimeBeforeOrderById(
            byte isActive, ModerationStatus moderationStatus, Date time, Pageable p);

    /********* ПОИСК САМЫХ НОВЫХ ПОСТОВ *********/
    @Query("SELECT p FROM Posts p WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time < CURRENT_TIME " +
            "ORDER BY p.time DESC")
    Page<Posts> findRecent(Pageable p);

    /********* ПОИСК САМЫХ СТАРЫХ ПОСТОВ *********/
    @Query("SELECT p FROM Posts p WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time < CURRENT_TIME " +
            "ORDER BY p.time ASC")
    Page<Posts> findEarly(Pageable p);

    /********* ПОИСК ПОСТОВ С НАИБОЛЬШИМ КОЛИЧЕСТВОМ КОММЕНТАРИЕВ *********/
    @Query(value = "SELECT * FROM posts p " +
            "JOIN (SELECT post_id, count(*) comments_amount FROM post_comments GROUP BY post_id) nt " +
            "ON p.id = nt.post_id " +
            "WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.time < CURRENT_TIME " +
            "ORDER BY nt.comments_amount DESC",
            nativeQuery = true)
    Page<Posts> findPopular(Pageable p);

    /********* ПОИСК ПОСТОВ С НАИБОЛЬШИМ КОЛИЧЕСТВОМ ЛАЙКОВ *********/
    @Query(value = "SELECT * FROM posts p " +
            "JOIN (SELECT post_id, count(*) likes_amount FROM post_votes  WHERE value > 0 GROUP BY post_id) nt " +
            "ON p.id = nt.post_id " +
            "WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.time < CURRENT_TIME " +
            "ORDER BY nt.likes_amount DESC",
            nativeQuery = true)
    Page<Posts> findBest(Pageable p);
}
