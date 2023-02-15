package com.employify.search;

import com.employify.dto.RequestStatisticsResponseDTO;
import com.employify.dto.SearchResponseDTO;
import com.employify.model.IndexUnit;
import com.employify.model.StatisticsUnit;
import com.employify.repository.DocumentRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticsResultRetriever {

	private final ElasticsearchRestTemplate elasticsearchRestTemplate;


	@Autowired
	public StatisticsResultRetriever(ElasticsearchRestTemplate elasticsearchRestTemplate){
		this.elasticsearchRestTemplate = elasticsearchRestTemplate;
	}

	public List<RequestStatisticsResponseDTO> getResults(QueryBuilder query, String statistic) throws Exception {
		checkQuery(query);
		SearchHits<StatisticsUnit> cvIndexHints = createRequest(query);
		return handleSearchHits(cvIndexHints, statistic);
	}

	private SearchHits<StatisticsUnit> createRequest(QueryBuilder query) {

//		BoolQueryBuilder mainBoolQuery = QueryBuilders.boolQuery();
//		mainBoolQuery.should(query);

		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(query)
				.build();

		return elasticsearchRestTemplate.search(searchQuery, StatisticsUnit.class, IndexCoordinates.of("logstashindex"));
	}

	private void checkQuery(QueryBuilder query) throws Exception {
		if (query == null) {
			throw new Exception("Query is null.");
		}
	}
	
	public List<RequestStatisticsResponseDTO>  handleSearchHits(SearchHits<StatisticsUnit> searchHits, String statistic) {
		HashMap<String, Integer> retResultsList = new HashMap<>();
		for (int i = 0; i < searchHits.getTotalHits(); i++) {
			StatisticsUnit statisticsIndexUnit = searchHits.getSearchHit(i).getContent();
			String city = statisticsIndexUnit.getMessage().split(statistic)[1];
			if(retResultsList.containsKey(city)){
				retResultsList.put(city, retResultsList.get(city)+1);
			}else{
				retResultsList.put(city, 1);
			}
		}
		return mapToResponseDTO(retResultsList);
	}

	private List<RequestStatisticsResponseDTO> mapToResponseDTO(HashMap<String, Integer> map){
		List<RequestStatisticsResponseDTO> response = new ArrayList<>();
		map = sortByValue(map);
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			response.add(new RequestStatisticsResponseDTO(entry.getKey(), entry.getValue()));
		}
		return response;
	}

	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
	{
		// Create a list from elements of HashMap
		List<Map.Entry<String, Integer> > list =
				new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
			public int compare(Map.Entry<String, Integer> o1,
							   Map.Entry<String, Integer> o2)
			{
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}

}
