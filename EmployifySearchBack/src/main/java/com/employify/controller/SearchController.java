package com.employify.controller;


import com.employify.dto.StandardQueryDTO;
import com.employify.model.SearchType;
import com.employify.search.QueryBuilderCustom;
import com.employify.search.ResultRetriever;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/search")
public class SearchController {

    private final ResultRetriever resultRetriever;

    @Autowired
    public SearchController(ResultRetriever resultRetriever) {
        this.resultRetriever = resultRetriever;
    }

    @PostMapping(value = "/standard")
    public ResponseEntity<?> searchStandardQuery(@RequestBody StandardQueryDTO queryDto) throws Exception {
        QueryBuilder query = QueryBuilderCustom.buildQuery(SearchType.REGULAR, queryDto.getField(), queryDto.getValue());
        return new ResponseEntity<>(resultRetriever.getResults(query), HttpStatus.OK);
    }
}
