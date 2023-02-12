package com.emploify.search.controller;

import com.emploify.search.dto.FileUploadDTO;
import com.emploify.search.indexer.Indexer;
import com.emploify.search.model.IndexUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("api/v1/index")
public class IndexerController {

    @Value("${dataDir}")
    private String DATA_DIR_PATH;


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

            String fileNameCv = saveUploadedFile(fileUploadDTO.getCv());
            String fileNameCl = saveUploadedFile(fileUploadDTO.getCoverLetter());
            if(fileNameCl != null && fileNameCv != null){
                IndexUnit indexUnit = new IndexUnit();
                indexUnit.setFirstName(fileUploadDTO.getFirstName());
                indexUnit.setLastName(fileUploadDTO.getLastName());
                indexUnit.setAddress(fileUploadDTO.getAddress());
                indexUnit.setEducation(fileUploadDTO.getEducation());
                indexer.add(indexUnit);
            }

    }

    private String saveUploadedFile(MultipartFile file) throws IOException {
        String retVal = null;
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            System.out.println(file.getOriginalFilename());
            File path = new File(DATA_DIR_PATH + file.getOriginalFilename());
            Files.write(Paths.get(path.getAbsolutePath()), bytes);
            retVal = path.toString();
        }
        return retVal;
    }

}
