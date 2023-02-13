package com.emploify.search.result;

import com.emploify.search.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
public class ResultRetriever {

    private final DocumentRepository repository;

    private final ElasticsearchOperations elasticsearchTemplate;

    @Autowired
    public ResultRetriever(DocumentRepository repository, ElasticsearchOperations elasticsearchTemplate) {
        this.repository = repository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }
}
