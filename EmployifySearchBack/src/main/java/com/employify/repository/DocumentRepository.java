package com.employify.repository;

import com.employify.model.IndexUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DocumentRepository extends ElasticsearchRepository<IndexUnit, String> {
}
