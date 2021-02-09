package root.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
