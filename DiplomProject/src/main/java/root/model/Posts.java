package root.model;

import org.apache.catalina.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Posts {

    /****** ПОЛЯ ******/
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(name = "is_active")
    private byte isActive;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ModerationStatus moderationStatus;

    @ManyToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "moderator_id", nullable = false)
    private Users moderator;

    @NotNull
    @ManyToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @NotNull
    private Date time;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String text;

    @NotNull
    private int viewCount;

    @OneToMany(mappedBy = "post")
    Set<Tag2Post> tag2Post;

    @OneToMany(targetEntity = PostVotes.class, mappedBy = "post")
    private List<PostVotes> votes;

    @OneToMany(targetEntity = PostComments.class, mappedBy = "post")
    private List<PostComments> comments;

    /****** GETTERS ******/
    public int getId() {
        return id;
    }

    public byte getIsActive() {
        return isActive;
    }

    public ModerationStatus getModerationStatus() {
        return moderationStatus;
    }

    public Users getModerator() {
        return moderator;
    }

    public Users getUser() {
        return user;
    }

    public Date getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getViewCount() {
        return viewCount;
    }

    public Set<Tag2Post> getTag2Post() {
        return tag2Post;
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

    public void setIsActive(byte isActive) {
        this.isActive = isActive;
    }

    public void setModerationStatus(ModerationStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public void setModerator(Users moderator) {
        this.moderator = moderator;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void setTag2Post(Set<Tag2Post> tag2Post) {
        this.tag2Post = tag2Post;
    }

    public void setVotes(List<PostVotes> votes) {
        this.votes = votes;
    }

    public void setComments(List<PostComments> comments) {
        this.comments = comments;
    }
}
