package root.repositories;

import org.springframework.data.repository.CrudRepository;
import root.model.GlobalSettings;

public interface GlobalSettingsRepository extends CrudRepository<GlobalSettings, Long> {
    GlobalSettings findByCode(String code);
}
