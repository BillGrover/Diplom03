package root.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InitDto {

    private String title;
    private String subtitle;
    private String phone;
    private String email;
    private String copyright;
    @JsonProperty("copyrightFrom")
    private String copyrightFrom;

    public InitDto() {
        this.title = "DevPub";
        this.subtitle = "Рассказы разработчиков";
        this.phone = "+7 917 737 16 60";
        this.email = "lev.bellendir@yandex.ru";
        this.copyright = "Лев Беллендир";
        this.copyrightFrom = "2021";
    }
}
