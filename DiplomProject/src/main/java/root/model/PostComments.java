package root.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class PostComments {

    /****** ПОЛЯ ******/
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "parent_id", nullable = true)
    private Integer parentId;

    @NotNull
    @ManyToOne(targetEntity = Posts.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", nullable = false)
    private Posts post;

    @NotNull
    @ManyToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

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

    public Posts getPost() {
        return post;
    }

    public Users getUser() {
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

    public void setPost(Posts post) {
        this.post = post;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setText(String text) {
        this.text = text;
    }
}
