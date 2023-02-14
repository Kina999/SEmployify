package com.employify.indexer;

import com.employify.model.IndexUnit;
import com.employify.repository.DocumentRepository;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class Indexer {

    @Value("${dataDir}")
    private String DATA_DIR_PATH;
    @Value("${dataDirF}")
    private String DATA_DIR_PATH_F;
    private final DocumentRepository documentRepository;

    @Autowired
    public Indexer(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public boolean add(IndexUnit unit){
        unit = documentRepository.save(unit);
        if(unit!=null){
            return true;
        }else{
            return false;
        }
    }

    public String parseFile(MultipartFile file) throws IOException {

        File f = new File(DATA_DIR_PATH + file.getOriginalFilename());
        String parsedText;
        PDFParser parser = new PDFParser(new RandomAccessFile(f, "r"));
        parser.parse();
        COSDocument cosDoc = parser.getDocument();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        PDDocument pdDoc = new PDDocument(cosDoc);
        parsedText = pdfStripper.getText(pdDoc);
        cosDoc.close();


        return parsedText;
    }

    public String saveUploadedFile(MultipartFile file) throws IOException {
        String retVal = null;
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            System.out.println(file.getOriginalFilename());
            File pathF = new File(DATA_DIR_PATH_F + file.getOriginalFilename());
            Files.write(Paths.get(pathF.getAbsolutePath()), bytes);

            File path = new File(DATA_DIR_PATH + file.getOriginalFilename());
            Files.write(Paths.get(path.getAbsolutePath()), bytes);
            retVal = path.toString();
        }
        return retVal;
    }
}
