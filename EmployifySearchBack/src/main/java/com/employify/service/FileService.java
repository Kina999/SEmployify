package com.employify.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class FileService {

     public ResponseEntity<Object> download(String filePath) {
        File file = new File(filePath);
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("File is not in target folder.");
        }

        System.out.println(file.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename="+file.getName());
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }

}
