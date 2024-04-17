package com.kid.anh_thunh_nas.service.serviceImpl;

import com.kid.anh_thunh_nas.exception.BadRequestExceptionCustom;
import com.kid.anh_thunh_nas.exception.NotFoundExceptionCustom;
import com.kid.anh_thunh_nas.service.AppFileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AppFileServiceImpl implements AppFileService {
    private final Path path = Paths.get("src/main/resources/images");
    @Override
    public String saveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        if (file.getOriginalFilename().contains(".png") || file.getOriginalFilename().contains(".jpg") || file.getOriginalFilename().contains(".jpeg")) {
            fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Files.copy(file.getInputStream(), path.resolve(fileName));
            return fileName;
        } else throw new BadRequestExceptionCustom("File must be contain jpg, png, jpeg");
    }

    @Override
    public Resource getFileByFileName(String fileName) throws IOException {
        Path path = Paths.get("src/main/resources/images/" + fileName);

        if (fileName.contains(".png") || fileName.contains(".jpg") || fileName.contains(".jpeg")) {
            ByteArrayResource byteArrayResource;
            try {
                byteArrayResource = new ByteArrayResource(Files.readAllBytes(path));
            } catch (NoSuchFileException ex) {
                throw new NotFoundExceptionCustom("File not founded");
            }
            return byteArrayResource;
        } else throw new BadRequestExceptionCustom("File must be contain jpg, png, jpeg");

    }
}
