package root.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class ApiGeneralController {

    @GetMapping("/init")
    @ResponseBody
    public String init(){
        return
        "{\"title\": \"DevPub\"," +
                "\"subtitle\": \"Рассказы разработчиков\"," +
                "\"phone\": \"+7 903 666-44-55\"," +
                "\"email\": \"mail@mail.ru\"," +
                "\"copyright\": \"Дмитрий Сергеев\"," +
                "\"copyrightFrom\": \"2005\"}";
    }
}
