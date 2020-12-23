package root.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class CaptchaCodes {

    /****** ПОЛЯ ******/
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotNull
    private Date time;

    @NotNull
    @Column(columnDefinition = "TINYTEXT")
    private String code;

    @NotNull
    @Column(columnDefinition = "TINYTEXT")
    private String secretCode;
}
