package com.example.movieApi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {

        //get file name
        String fileName = file.getOriginalFilename();

        //to get the file path - using File.separator makes the separator portable across different os
        String filePath = path + File.separator + fileName;

        //create file object
        File f = new File(path);
        if(!f.exists()){

            f.mkdir();
        }

        //copy or upload the file to path
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;

        return new FileInputStream(filePath);
    }
}
