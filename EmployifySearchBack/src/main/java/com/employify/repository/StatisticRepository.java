package com.employify.repository;

import com.employify.model.StatisticsUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StatisticRepository extends ElasticsearchRepository<StatisticsUnit, String> {
}
