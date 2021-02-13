package root.services;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import root.dto.ErrorsDto;
import root.dtoResponses.SimpleResponse;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${upload.path}")
    private String uploadPath;
    @Value("${maxFileSize}")
    private long maxFileSize;
    private final int SUB_DIR_NAME_LENGTH = 3;
    private final int SUBDIRECTORY_AMOUNT = 3;

    public ResponseEntity<?> store(MultipartFile file) {
        if (file == null)
            return badRequest("File not found");

        if (!getFileExtension(file).equals("jpg") && !getFileExtension(file).equals("png"))
            return badRequest("Допускаются только форматы jpg и png.");

        if (file.getSize() > maxFileSize)
            return badRequest("Размер файла превышает допустимый размер (" + maxFileSize / 1024 + ") kB.");

        uploadPath = updatePath(uploadPath, 0);

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();
        String result = uploadPath + "//" + resultFilename;

        try {
            file.transferTo(new File(result));
        } catch (IOException e) {
            System.out.println("Ошибка сохранения файла\n" + e.getMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private String updatePath(String uploadPath, int currentSubdirectoryAmount) {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())
            uploadDir.mkdir();

        if (currentSubdirectoryAmount < SUBDIRECTORY_AMOUNT){
            currentSubdirectoryAmount++;
            uploadPath = updatePath(
                    uploadPath + "//" + RandomStringUtils.random(SUB_DIR_NAME_LENGTH, true, false),
                    currentSubdirectoryAmount);
        }
        return uploadPath;
    }

    private ResponseEntity<?> badRequest(String errorText) {
        return new ResponseEntity<>(
                new SimpleResponse(
                        false,
                        new ErrorsDto(null, null, errorText)
                ), HttpStatus.BAD_REQUEST);
    }

    private static String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else {
            return "";
        }
    }
}
