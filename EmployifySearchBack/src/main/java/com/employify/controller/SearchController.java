package com.employify.controller;


import com.employify.dto.BoolQueryDTO;
import com.employify.dto.GeoQueryDTO;
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
        try {
            QueryBuilder query = QueryBuilderCustom.buildQuery(queryDto.isPhrase() ? SearchType.PHRASE : SearchType.REGULAR, queryDto.getField(), queryDto.getValue());
            return new ResponseEntity<>(resultRetriever.getResults(query, resultRetriever.getHighlightBuilder(queryDto.getField())), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/bool")
    public ResponseEntity<?> searchBoolQuery(@RequestBody BoolQueryDTO queryDto) throws Exception {
        try {
            QueryBuilder query = QueryBuilderCustom.buildBoolQuery(queryDto);
            return new ResponseEntity<>(resultRetriever.getResults(query,
                    resultRetriever.getBoolHighlightBuilder(queryDto.getFirstField(), queryDto.getSecondField())), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/geo")
    public ResponseEntity<?> searchGeoQuery(@RequestBody GeoQueryDTO queryDto) throws Exception {
        try {
            QueryBuilder query = QueryBuilderCustom.buildGeoQuery(queryDto);
            return new ResponseEntity<>(resultRetriever.getResults(query,null), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
