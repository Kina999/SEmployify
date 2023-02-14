package com.employify.controller;


import com.employify.dto.BoolQueryDTO;
import com.employify.dto.StandardQueryDTO;
import com.employify.model.SearchType;
import com.employify.search.QueryBuilderCustom;
import com.employify.search.ResultRetriever;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/search")
public class SearchController {

    private final ResultRetriever resultRetriever;

    @Autowired
    public SearchController(ResultRetriever resultRetriever) {
        this.resultRetriever = resultRetriever;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/standard")
    public ResponseEntity<?> searchStandardQuery(@RequestBody StandardQueryDTO queryDto) throws Exception {
        QueryBuilder query = QueryBuilderCustom.buildQuery(SearchType.REGULAR, queryDto.getField(), queryDto.getValue());
        return new ResponseEntity<>(resultRetriever.getResults(query, resultRetriever.getHighlightBuilder(queryDto.getField())), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/bool")
    public ResponseEntity<?> searchBoolQuery(@RequestBody BoolQueryDTO queryDto) throws Exception {
        QueryBuilder query = QueryBuilderCustom.buildBoolQuery(queryDto);
        //return new ResponseEntity<>(resultRetriever.getResults(query, resultRetriever.getHighlightBuilder(queryDto.getField())), HttpStatus.OK);
        return null;
    }
}
