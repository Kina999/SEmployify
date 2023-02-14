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
			return createNameLastNameQuery(value, queryType);
		}else if(queryType.equals(SearchType.REGULAR)){
			return QueryBuilders.matchQuery(field, value);
		} else if(queryType.equals(SearchType.PHRASE)) {
			return QueryBuilders.matchPhraseQuery(field, value);
		}
		return null;
	}

	public static QueryBuilder buildBoolQuery(BoolQueryDTO queryDto){

		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		QueryBuilder firstField;
		QueryBuilder secondField;

		if(queryDto.isFirstPhrase()){firstField = QueryBuilders.matchPhraseQuery(queryDto.getFirstField(), queryDto.getFirstValue());
		}else{firstField = QueryBuilders.matchQuery(queryDto.getFirstField(), queryDto.getFirstValue());}

		if(queryDto.isSecondPhrase()){secondField = QueryBuilders.matchPhraseQuery(queryDto.getSecondField(), queryDto.getSecondValue());
		}else{secondField = QueryBuilders.matchQuery(queryDto.getSecondField(), queryDto.getSecondValue());}

		if(queryDto.isOr()){boolQuery.should(firstField);boolQuery.should(secondField);
		}else{boolQuery.must(firstField);boolQuery.must(secondField);}

		return boolQuery;
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

	private static QueryBuilder createNameLastNameQuery(String value, SearchType queryType){
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		String name = value.split("!")[0];;
		String lastName = "";
		try{lastName = value.split("!")[1];
		}catch(Exception e){lastName = null;}
		QueryBuilder nameQB;
		QueryBuilder lastNameQB;
		if(queryType.equals(SearchType.REGULAR)){
			nameQB = QueryBuilders.fuzzyQuery("firstName", name);
			lastNameQB = QueryBuilders.fuzzyQuery("firstName", name);
		} else {
			nameQB = QueryBuilders.matchPhraseQuery("firstName", name);
			lastNameQB = QueryBuilders.matchPhraseQuery("firstName", name);
		}
		if(!name.equals("")){boolQuery.must(nameQB);}
		if(lastName != null){boolQuery.must(lastNameQB);}
		return boolQuery;
	}

}
