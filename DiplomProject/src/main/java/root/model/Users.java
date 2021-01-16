package root.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class Users {

    /****** ПОЛЯ ******/
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotNull
    private byte isModerator;

    @NotNull
    private Date regTime;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String code;

    @Column(columnDefinition = "TEXT")
    private String photo;

    @OneToMany(targetEntity = Posts.class, mappedBy = "user")
    private List<Posts> posts;

    @OneToMany(targetEntity = Posts.class, mappedBy = "moderator")
    private List<Posts> moderatedPosts;

    @OneToMany(targetEntity = PostVotes.class, mappedBy = "user")
    private List<PostVotes> votes;

    @OneToMany(targetEntity = PostComments.class, mappedBy = "user")
    private List<PostComments> comments;

    /****** ГЕТТЕРЫ ******/
    public int getId() {
        return id;
    }

    public byte getIsModerator() {
        return isModerator;
    }

    public Date getRegTime() {
        return regTime;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCode() {
        return code;
    }

    public String getPhoto() {
        return photo;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public List<Posts> getModeratedPosts() {
        return moderatedPosts;
    }

    public List<PostVotes> getVotes() {
        return votes;
    }

    public List<PostComments> getComments() {
        return comments;
    }

    /****** СЕТТЕРЫ ******/
    public void setId(int id) {
        this.id = id;
    }

    public void setIsModerator(byte isModerator) {
        this.isModerator = isModerator;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }

    public void setModeratedPosts(List<Posts> moderatedPosts) {
        this.moderatedPosts = moderatedPosts;
    }

    public void setVotes(List<PostVotes> votes) {
        this.votes = votes;
    }

    public void setComments(List<PostComments> comments) {
        this.comments = comments;
    }
}
