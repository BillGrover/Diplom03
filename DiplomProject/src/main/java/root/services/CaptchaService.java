package root.services;

import com.github.cage.Cage;
import com.github.cage.image.Painter;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;
import root.dtoResponses.CaptchaResponse;
import root.model.CaptchaCode;
import root.repositories.CaptchaCodeRepository;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class CaptchaService {

    CaptchaCodeRepository captchaCodeRepo;

    public CaptchaService(CaptchaCodeRepository captchaCodeRepo) {
        this.captchaCodeRepo = captchaCodeRepo;
    }

    public CaptchaResponse createCaptcha() {
        Painter painter = new Painter(100, 35, null, null, null, null);
        Cage cage = new Cage(painter, null, null, null, null, null, null);

        String captchaText = RandomStringUtils.random(4, true, true);
        byte[] fileContent = cage.draw(captchaText);
        String stringImage = Base64.getEncoder().encodeToString(fileContent);
        String secretCode = UUID.randomUUID().toString();

        CaptchaCode captchaCode = new CaptchaCode();
        captchaCode.setCode(captchaText.toLowerCase());
        captchaCode.setSecretCode(secretCode);
        captchaCode.setTime(new Date());

        captchaCodeRepo.save(captchaCode);

        return new CaptchaResponse(secretCode, stringImage);
    }

    public boolean validateCaptcha(String textFromFrontend, String secret){
        String textFromDB = captchaCodeRepo.findBySecretCode(secret).getCode();
        return textFromFrontend.toLowerCase().equals(textFromDB);
    }
}
