package root.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.RandomStringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    /****** ПОЛЯ ******/
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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

    @JsonIgnore
    @OneToMany(targetEntity = Post.class, mappedBy = "user")
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(targetEntity = Post.class, mappedBy = "moderator")
    private List<Post> moderatedPosts;

    @JsonIgnore
    @OneToMany(targetEntity = PostVote.class, mappedBy = "user")
    private List<PostVote> votes;

    @JsonIgnore
    @OneToMany(targetEntity = PostComment.class, mappedBy = "user")
    private List<PostComment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    Set<Role2User> role2Users;


    public User() {
    }

    public User(
            @NotNull String name,
            @NotNull String email,
            @NotNull String password) {
        this.isModerator = 0;
        this.regTime = new Date();
        this.name = name;
        this.email = email;
        this.password = password;
        this.code = RandomStringUtils.random(5, true, true);
    }

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

    public List<Post> getPosts() {
        return posts;
    }

    public List<Post> getModeratedPosts() {
        return moderatedPosts;
    }

    public List<PostVote> getVotes() {
        return votes;
    }

    public List<PostComment> getComments() {
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

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setModeratedPosts(List<Post> moderatedPosts) {
        this.moderatedPosts = moderatedPosts;
    }

    public void setVotes(List<PostVote> votes) {
        this.votes = votes;
    }

    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }

    public Set<Role2User> getRole2Users() {
        return role2Users;
    }

    public void setRole2Users(Set<Role2User> role2Users) {
        this.role2Users = role2Users;
    }
}
