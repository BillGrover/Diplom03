package root.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import root.enums.ModerationStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {

    /****** ПОЛЯ ******/
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "is_active")
    private byte isActive;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ModerationStatus moderationStatus;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "moderator_id", nullable = false)
    private User moderator;

    @NotNull
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private Date time;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String text;

    @NotNull
    private int viewCount;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    Set<Tag2Post> tag2Post;

    @JsonIgnore
    @OneToMany(targetEntity = PostVote.class, mappedBy = "post")
    private List<PostVote> votes;

    @JsonIgnore
    @OneToMany(targetEntity = PostComment.class, mappedBy = "post")
    private List<PostComment> comments;

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

    public User getModerator() {
        return moderator;
    }

    public User getUser() {
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

    public void setIsActive(byte isActive) {
        this.isActive = isActive;
    }

    public void setModerationStatus(ModerationStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    public void setUser(User user) {
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

    public void setVotes(List<PostVote> votes) {
        this.votes = votes;
    }

    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }
}
