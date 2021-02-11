package root.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "captcha_codes")
public class CaptchaCode {

    /****** ПОЛЯ ******/
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotNull
    private Date time;

    @NotNull
    @Column(columnDefinition = "TINYTEXT")
    private String code;

    @NotNull
    @Column(columnDefinition = "TINYTEXT")
    private String secretCode;

    public CaptchaCode() {
    }

    /****** ГЕТТЕРЫ ******/
    public int getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    public String getCode() {
        return code;
    }

    public String getSecretCode() {
        return secretCode;
    }

    /****** СЕТТЕРЫ ******/
    public void setId(int id) {
        this.id = id;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }
}
