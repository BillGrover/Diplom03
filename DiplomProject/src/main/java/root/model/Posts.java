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
}
