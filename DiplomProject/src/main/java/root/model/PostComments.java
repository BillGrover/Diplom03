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

    private int parentId;

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
}
