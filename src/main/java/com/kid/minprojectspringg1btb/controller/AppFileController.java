package com.kid.minprojectspringg1btb.controller;

import com.kid.minprojectspringg1btb.model.dto.response.AppFileResponse;
import com.kid.minprojectspringg1btb.model.dto.response.FileUploadResponse;
import com.kid.minprojectspringg1btb.model.entity.FileUpload;
import com.kid.minprojectspringg1btb.service.AppFileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/files")
public class AppFileController {
    private final AppFileService  appFileService;
    public AppFileController(AppFileService appFileService) {
        this.appFileService = appFileService;
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Post Image")
    public ResponseEntity<?> postImage(@RequestParam MultipartFile file) throws IOException{
        String fileName = appFileService.saveFile(file);
        String fileUrl = "http://localhost:8080/" + fileName;
        FileUpload fileResponse = new FileUpload(fileName,fileUrl,file.getContentType(),file.getSize());
        FileUploadResponse<FileUpload> response = FileUploadResponse.<FileUpload>builder()
                .message("successfully uploaded file")

                .httpStatus(HttpStatus.OK)

                .payload(fileResponse).build();

        return ResponseEntity.ok(response);
    }




    @GetMapping()
    public ResponseEntity<?> getFile(@RequestParam String fileName) throws IOException {

        Resource resource = appFileService.getFileByFileName(fileName);

        MediaType mediaType;

        if (fileName.endsWith(".pdf")){mediaType = MediaType.APPLICATION_PDF;}

        else if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") ||
                fileName.endsWith(".gif")){

            mediaType = MediaType.IMAGE_PNG;}

        else {mediaType = MediaType.APPLICATION_OCTET_STREAM;}

        return ResponseEntity.ok()

                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")

                .contentType(mediaType).body(resource);

    }

}
