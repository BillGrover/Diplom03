package root.repositories;

import org.springframework.data.repository.CrudRepository;
import root.model.CaptchaCode;

public interface CaptchaCodeRepository extends CrudRepository<CaptchaCode, Long> {

    CaptchaCode findBySecretCode(String secretCode);
}
