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

    /****** ГЕТТЕРЫ ******/
    public int getId() {
        return id;
    }

    public Posts getPost() {
        return post;
    }

    public Tags getTag() {
        return tag;
    }

    /****** СЕТТЕРЫ ******/
    public void setId(int id) {
        this.id = id;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
    }
}
