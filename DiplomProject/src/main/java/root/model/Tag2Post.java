package root.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Tag2Post {

    /****** ПОЛЯ ******/
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts post;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tags tag;
}
