package root.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class GlobalSettings {

    /****** ПОЛЯ ******/
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    @NotNull
    private String value;

    /*
    Настройки
    MULTIUSER_MODE          Многопользовательский режим         YES / NO
    POST_PREMODERATION      Премодерация постов                 YES / NO
    STATISTICS_IS_PUBLIC    Показывать всем статистику блога    YES / NO
     */
}