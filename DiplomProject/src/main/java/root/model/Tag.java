package root.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {

    /****** ПОЛЯ ******/
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "tag")
    Set<Tag2Post> tag2Post;

    public Tag(@NotNull String title) {
        this.title = title;
    }

    public Tag() {
    }

    /****** ГЕТТЕРЫ ******/
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Set<Tag2Post> getTag2Post() {
        return tag2Post;
    }

    /****** СЕТТЕРЫ ******/
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTag2Post(Set<Tag2Post> tag2Post) {
        this.tag2Post = tag2Post;
    }
}
