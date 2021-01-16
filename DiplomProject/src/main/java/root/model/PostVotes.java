package root.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class PostVotes {    //Лайки и дизлайки

    /****** ПОЛЯ ******/
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotNull
    @ManyToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @NotNull
    @ManyToOne(targetEntity = Posts.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", nullable = false)
    private Posts post;

    @NotNull
    private Date time;

    @NotNull
    private byte value; // "1" - лайк, "-1" - дизлайк

    /****** ГЕТТЕРЫ ******/
    public int getId() {
        return id;
    }

    public Users getUser() {
        return user;
    }

    public Posts getPost() {
        return post;
    }

    public Date getTime() {
        return time;
    }

    public byte getValue() {
        return value;
    }

    /****** СЕТТЕРЫ ******/
    public void setId(int id) {
        this.id = id;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setValue(byte value) {
        this.value = value;
    }
}
