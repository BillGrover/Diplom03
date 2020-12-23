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
}
