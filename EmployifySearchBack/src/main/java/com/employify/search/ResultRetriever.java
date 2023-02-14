package com.employify.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.employify.dto.SearchResponseDTO;
import com.employify.model.IndexUnit;
import com.employify.repository.DocumentRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class ResultRetriever {

	private final ElasticsearchRestTemplate elasticsearchRestTemplate;

	private final DocumentRepository documentRepository;

	@Autowired
	public ResultRetriever(ElasticsearchRestTemplate elasticsearchRestTemplate, DocumentRepository documentRepository){
		this.elasticsearchRestTemplate = elasticsearchRestTemplate;
		this.documentRepository = documentRepository;
	}

	public List<SearchResponseDTO> getResults(QueryBuilder query, HighlightBuilder highlightBuilder) throws Exception {
		checkQuery(query);
		SearchHits<IndexUnit> cvIndexHints = createRequest(query, highlightBuilder);
		return handleSearchHits(cvIndexHints);
	}

	private SearchHits<IndexUnit> createRequest(QueryBuilder query, HighlightBuilder highlightBuilder) {

//		BoolQueryBuilder mainBoolQuery = QueryBuilders.boolQuery();
//		mainBoolQuery.should(query);

		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(query)
				.withHighlightBuilder(highlightBuilder)
				.build();

		return elasticsearchRestTemplate.search(searchQuery, IndexUnit.class, IndexCoordinates.of("agencija_za_zaposljavanje_dokumenti"));
	}

	private void checkQuery(QueryBuilder query) throws Exception {
		if (query == null) {
			throw new Exception("Query is null.");
		}
	}
	
	public List<SearchResponseDTO> handleSearchHits(SearchHits<IndexUnit> searchHits) {
		List<SearchResponseDTO> retResultsList = new ArrayList<>();
		for (int i = 0; i < searchHits.getTotalHits(); i++) {
			IndexUnit cvIndexUnit = searchHits.getSearchHit(i).getContent();
			Map<String, List<String>> highlightFields = searchHits.getSearchHit(i).getHighlightFields();

			StringBuilder highlights = new StringBuilder("...");
			if (searchHits.getSearchHit(i).getHighlightFields() != null) {
				highlightFields.forEach((key, value) -> highlights.append(value));
			}
				retResultsList.add(mapCVIndexToResultData(cvIndexUnit, highlights.toString()));

			}
		return retResultsList;
	}
	
	private SearchResponseDTO mapCVIndexToResultData(IndexUnit cvIndexUnit, String h) {
		SearchResponseDTO resultData = mapCVToDTO(cvIndexUnit, h);
		return resultData;
	}

	public SearchResponseDTO mapCVToDTO(IndexUnit cvIndexUnit, String h) {
		SearchResponseDTO resultData = new SearchResponseDTO();
		resultData.setHighlight("");
		resultData.setFirstName(cvIndexUnit.getFirstName());
		resultData.setLastName(cvIndexUnit.getLastName());
		resultData.setAddress(cvIndexUnit.getAddress());
		resultData.setEducation(cvIndexUnit.getEducation());
		resultData.setCvPath(cvIndexUnit.getCvPath());
		resultData.setCvContent(cvIndexUnit.getCvContent());
		resultData.setClPath(cvIndexUnit.getClPath());
		resultData.setClContent(cvIndexUnit.getCoverLetterContent());
		resultData.setHighlight(h);
		return resultData;
	}

	public HighlightBuilder getHighlightBuilder(String field){

		HighlightBuilder highlightBuilder = new HighlightBuilder();
		if(field.equals("firstNameAndLastName")){
			highlightBuilder.field("firstName");
			highlightBuilder.field("lastName");
		}else{
			highlightBuilder.field(field);
		}
		highlightBuilder.preTags("<b>");
		highlightBuilder.postTags("</b>");
		highlightBuilder.numOfFragments(3);
		highlightBuilder.fragmentSize(150);
		return highlightBuilder;
	}

}
