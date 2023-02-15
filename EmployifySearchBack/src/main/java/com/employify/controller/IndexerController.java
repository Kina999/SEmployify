package com.employify.controller;

import com.employify.dto.FileUploadDTO;
import com.employify.indexer.Indexer;
import com.employify.model.IndexUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/index")
public class IndexerController {

    private final Indexer indexer;

    @Autowired
    public IndexerController(Indexer indexer) {
        this.indexer = indexer;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = {"/add"})
    public ResponseEntity<?> addFile(@RequestPart("user") FileUploadDTO fileUploadDTO, @RequestPart("cv") MultipartFile cv, @RequestPart("coverLetter") MultipartFile coverLetter){
        try {
            fileUploadDTO.setCv(cv);
            fileUploadDTO.setCoverLetter(coverLetter);
            indexer.indexUploadedFile(fileUploadDTO);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }





}