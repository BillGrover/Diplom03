package root.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import root.dto.GlobalSettingsDto;
import root.dto.InitDto;
import root.dtoResponses.TagResponse;
import root.services.GlobalSettingsService;
import root.services.TagService;

@Controller
@RequestMapping("/api")
public class ApiGeneralController {

    private final GlobalSettingsService globalSettingsService;
    private final TagService tagService;

    public ApiGeneralController(GlobalSettingsService globalSettingsService, TagService tagService) {
        this.globalSettingsService = globalSettingsService;
        this.tagService = tagService;
    }

    @GetMapping("/init")
    public ResponseEntity<InitDto> init() {
        return ResponseEntity.ok(new InitDto());
    }

    @GetMapping("/settings")
    public ResponseEntity<GlobalSettingsDto> settings() {
        return globalSettingsService.getSettings();
    }

    @GetMapping("/tag")
    public ResponseEntity<TagResponse> tags(@RequestParam(required = false) String query) {
        return tagService.getTags(query);
    }
}
