package com.emploify.search.repository;

import com.emploify.search.model.IndexUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DocumentRepository extends ElasticsearchRepository<IndexUnit, String> {
}
