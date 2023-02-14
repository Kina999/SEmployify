package com.employify.search;

import com.employify.dto.BoolQueryDTO;
import com.employify.model.IndexUnit;
import com.employify.model.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryBuilderCustom {
	
	private static int maxEdits = 1;
	
	public static QueryBuilder buildQuery(SearchType queryType, String field, String value) throws IllegalArgumentException {
		validateQueryFields(field, value);
		if(field.equals("firstNameAndLastName")){
			return createNameLastNameQuery(value);
		}else if(queryType.equals(SearchType.REGULAR)){
			return QueryBuilders.fuzzyQuery(field, value);
		} else if(queryType.equals(SearchType.PHRASE)) {
			return QueryBuilders.matchPhraseQuery(field, value);
		}
		return null;
	}

	public static QueryBuilder buildBoolQuery(BoolQueryDTO queryDto){

		return null;
	}

	private static void validateQueryFields(String field, String value) {
		String errorMessage = "";
		if(field == null || field.equals("")){
			errorMessage += "Field not specified";
		}
		if(value == null){
			if(!errorMessage.equals("")) errorMessage += "\n";
			errorMessage += "Value not specified";
		}
		if(!errorMessage.equals("")){
			throw new IllegalArgumentException(errorMessage);
		}
	}

	private static QueryBuilder createNameLastNameQuery(String value){
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		String name = value.split("!")[0];;
		String lastName = "";
		try{lastName = value.split("!")[1];
		}catch(Exception e){lastName = null;}
		if(!name.equals("")){boolQuery.should(QueryBuilders.fuzzyQuery("firstName", name));}
		if(lastName != null){boolQuery.should(QueryBuilders.fuzzyQuery("lastName", lastName));}
		return boolQuery;
	}

}
