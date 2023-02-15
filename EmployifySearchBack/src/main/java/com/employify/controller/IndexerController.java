package com.employify.controller;

import com.employify.dto.AddEmployeeDTO;
import com.employify.dto.CityDTO;
import com.employify.dto.FileUploadDTO;
import com.employify.indexer.Indexer;
import com.employify.model.IndexUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("api/v1/index")
public class IndexerController {

    private final Indexer indexer;
    private static final Logger logger = LoggerFactory.getLogger(IndexerController.class);

    @Autowired
    public IndexerController(Indexer indexer) {
        this.indexer = indexer;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = {"/add"})
    public ResponseEntity<?> addFile(@RequestPart("user") FileUploadDTO fileUploadDTO, @RequestPart("cv") MultipartFile cv,
                                     @RequestPart("coverLetter") MultipartFile coverLetter, HttpServletRequest request){
        try {

            String ipAddress = request.getHeader("X-Forwarded-For");
            RestTemplate restTemplate = new RestTemplate();
            String uri = "https://api.ip2loc.com/4weQp8ARbhqTpfyU0Vj4qYuUxY4T10s1/"+ipAddress+"?include=city";
            CityDTO city = restTemplate.getForObject(uri, CityDTO.class);
            logger.info("CITY=" + city.getCity());
            fileUploadDTO.setCv(cv);
            fileUploadDTO.setCoverLetter(coverLetter);
            indexer.indexUploadedFile(fileUploadDTO);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = {"/add/employee"})
    public ResponseEntity<?> addEmployee(@RequestBody AddEmployeeDTO addEmployeeDTO){
        try {
            logger.info("EMPLOYEE=" + addEmployeeDTO.getEmployee());
            logger.info("COMPANY=" + addEmployeeDTO.getCompany());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

}