package root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import root.services.ImageService;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> handleFileUpload(@RequestParam("image") MultipartFile image){
        return imageService.store(image);
    }
}
