package com.kid.minprojectspringg1btb.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AppFileService {
//    String saveFile(MultipartFile file) throws IOException;
//    Resource getFileByFileName(String fileName) throws IOException;

    String saveFile(MultipartFile file) throws IOException;

    Resource getFileByFileName(String fileName) throws IOException;
}
