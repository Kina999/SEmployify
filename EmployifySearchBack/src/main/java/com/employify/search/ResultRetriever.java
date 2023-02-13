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

	public List<SearchResponseDTO> getResults(QueryBuilder query) throws Exception {
		checkQuery(query);
		SearchHits<IndexUnit> cvIndexHints = createRequest(query);
		return handleSearchHits(cvIndexHints);
	}

	private SearchHits<IndexUnit> createRequest(QueryBuilder query) {

		BoolQueryBuilder mainBoolQuery = QueryBuilders.boolQuery();
		mainBoolQuery.should(query);
		//HighlightBuilder highlightBuilder = new HighlightBuilder();
		//highlightBuilder.field("cvContent");
		//highlightBuilder.field("clPath");
		//highlightBuilder.preTags("<em>");
		//highlightBuilder.postTags("</em>");
		//highlightBuilder.numOfFragments(3);
		//highlightBuilder.fragmentSize(150);

		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(mainBoolQuery)
				.withHighlightBuilder(null)
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
			if (searchHits.getSearchHit(i).getHighlightFields() != null) {
				StringBuilder highlights = new StringBuilder("...");
			}
			// searchHits.getSearchHit(i).getHighlightFields().get("cvContent").get(0)
			retResultsList.add(mapCVIndexToResultData(cvIndexUnit, highlightFields));

			}
		return retResultsList;
	}
	
	private SearchResponseDTO mapCVIndexToResultData(IndexUnit cvIndexUnit, Map<String, List<String>> highlightFields) {
		SearchResponseDTO resultData = mapCVToDTO(cvIndexUnit, new ArrayList<>());
		highlightFields.forEach((key, value) -> setResultDataForHighlight(resultData, key, value));
		return resultData;
	}
	
	private void setResultDataForHighlight(SearchResponseDTO resultData, String key, List<String> value) {
		switch (key) {
			case "firstName":
				resultData.setFirstName(formatHighlight(value));
				break;
			case "lastName":
				resultData.setLastName(formatHighlight(value));
				break;
			case "text":
				resultData.setHighlight(formatHighlight(value));
				break;
		}
	}

	
	public SearchResponseDTO mapCVToDTO(IndexUnit cvIndexUnit, List<String> highlight) {
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
		resultData.setHighlight(formatHighlight(highlight));
		return resultData;
	}
	
	public String formatHighlight(List<String> highlight) {
		StringBuilder stringBuilder = new StringBuilder();
		highlight.forEach(highlightItem -> stringBuilder.append(highlightItem).append("<br/>"));
		return stringBuilder.toString();
	}

}
