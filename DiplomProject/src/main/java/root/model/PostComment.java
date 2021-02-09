package root.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "post_comments")
public class PostComment {

    /****** ПОЛЯ ******/
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "parent_id", nullable = true)
    private Integer parentId;

    @NotNull
    @ManyToOne(targetEntity = Post.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @NotNull
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private Date time;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String text;

    /****** ГЕТТЕРЫ ******/
    public int getId() {
        return id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public Post getPost() {
        return post;
    }

    public User getUser() {
        return user;
    }

    public Date getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    /****** СЕТТЕРЫ ******/
    public void setId(int id) {
        this.id = id;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setText(String text) {
        this.text = text;
    }
}
