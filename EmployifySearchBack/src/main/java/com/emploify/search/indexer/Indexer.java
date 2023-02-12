package com.emploify.search.indexer;

import com.emploify.search.model.IndexUnit;
import com.emploify.search.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Indexer {

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
}
