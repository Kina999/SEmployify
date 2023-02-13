package com.emploify.search.result;

import com.emploify.search.model.SearchType;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

public class CustomQueryBuilder {
    public static QueryBuilder buildQuery(SearchType type, String field, String value) {
        String errorMessage = "";
        if (field == null || field.equals("")) {
            errorMessage += "Field not specified";
        }
        if (value == null) {
            if (!errorMessage.equals("")) errorMessage += "\n";
            errorMessage += "Value not specified";
        }
        if (!errorMessage.equals("")) {
            throw new IllegalArgumentException(errorMessage);
        }
        QueryBuilder retVal = null;
        if(SearchType.REGULAR.equals(type)) {
            //Regular Search
            retVal = QueryBuilders.matchQuery(field, value);
        }else if(SearchType.PHRASE.equals(type)){
            //Fraze
            retVal = QueryBuilders.matchPhraseQuery(field,value);
        }
        if("ImeIPrezime".equals(field)) {
            retVal = nestedQuery("user", boolQuery()
                    .should(matchQuery("user.firstName", value))
                    .should(matchQuery("user.lastName", value)), ScoreMode.Avg);
        }
        return  retVal;
    }
}
