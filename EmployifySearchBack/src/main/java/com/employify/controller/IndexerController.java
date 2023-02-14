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
            indexUploadedFile(fileUploadDTO);

        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    private void indexUploadedFile(FileUploadDTO fileUploadDTO) throws IOException{

        String fileNameCv = indexer.saveUploadedFile(fileUploadDTO.getCv());
        String fileNameCl = indexer.saveUploadedFile(fileUploadDTO.getCoverLetter());
        if(fileNameCl != null && fileNameCv != null){
            IndexUnit indexUnit = new IndexUnit();
            indexUnit.setFirstName(fileUploadDTO.getFirstName());
            indexUnit.setLastName(fileUploadDTO.getLastName());
            indexUnit.setGeoPoint(new GeoPoint(fileUploadDTO.getLatitude(), fileUploadDTO.getLongitude()));
            indexUnit.setEducation(fileUploadDTO.getEducation());
            indexUnit.setCvPath(fileUploadDTO.getCv().getOriginalFilename());
            indexUnit.setCvContent(indexer.parseFile(fileUploadDTO.getCv()));
            indexUnit.setClPath(fileUploadDTO.getCoverLetter().getOriginalFilename());
            indexUnit.setCoverLetterContent(indexer.parseFile(fileUploadDTO.getCoverLetter()));
            indexer.add(indexUnit);
        }

    }



}