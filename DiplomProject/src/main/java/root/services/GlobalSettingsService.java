package root.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import root.dto.GlobalSettingsDto;
import root.repositories.GlobalSettingsRepository;

@Service
public class GlobalSettingsService {

    GlobalSettingsRepository globalSettingsRepository;

    public GlobalSettingsService(GlobalSettingsRepository globalSettingsRepository) {
        this.globalSettingsRepository = globalSettingsRepository;
    }

    public ResponseEntity<GlobalSettingsDto> getSettings() {
        return new ResponseEntity<>(
                new GlobalSettingsDto(
                        globalSettingsRepository.findByCode("MULTIUSER_MODE").getValue().equals("YES"),
                        globalSettingsRepository.findByCode("POST_PREMODERATION").getValue().equals("YES"),
                        globalSettingsRepository.findByCode("STATISTICS_IS_PUBLIC").getValue().equals("YES")
                ), HttpStatus.OK);
    }
}
