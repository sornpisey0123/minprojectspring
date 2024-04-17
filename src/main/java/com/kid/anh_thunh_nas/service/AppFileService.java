package com.kid.anh_thunh_nas.service;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AppFileService {
    String saveFile(MultipartFile file) throws IOException;
    Resource getFileByFileName(String fileName) throws IOException;
}
