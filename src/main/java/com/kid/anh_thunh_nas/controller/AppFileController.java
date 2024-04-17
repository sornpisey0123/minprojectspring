package com.kid.anh_thunh_nas.controller;

import com.kid.anh_thunh_nas.model.dto.response.AppFileResponse;
import com.kid.anh_thunh_nas.service.AppFileService;
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


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/upload")
    public ResponseEntity<AppFileResponse> uploadFile(@RequestParam MultipartFile file) throws IOException {
        String fileName = appFileService.saveFile(file);
        AppFileResponse appFileResponse = new AppFileResponse(fileName, file.getContentType(), file.getSize());
        return ResponseEntity.status(HttpStatus.OK).body(appFileResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAllFiles(@RequestParam String fileName) throws IOException {
        Resource resource = appFileService.getFileByFileName(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }

}
