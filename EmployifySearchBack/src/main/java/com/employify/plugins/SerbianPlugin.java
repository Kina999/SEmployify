/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.employify.plugins;

import java.util.Collections;
import java.util.Map;

import com.employify.indexer.analyzers.SerbianAnalyzerProvider;
import com.employify.indexer.analyzers.SerbianTokenFilterFactory;
import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.AnalyzerProvider;
import org.elasticsearch.index.analysis.TokenFilterFactory;
import org.elasticsearch.indices.analysis.AnalysisModule.AnalysisProvider;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;


public class SerbianPlugin extends Plugin implements AnalysisPlugin {

	public static final String ANALYZER_NAME = "serbian";
	public static final String FILTER_NAME = "serbian-stem";

	public String name() {
		return "serbian-plugin";
	}

	public String description() {
		return "Analyzer that converts cyrilic words into lowercase latinic";
	}

	@Override
	public Map<String, AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
		AnalysisProvider<TokenFilterFactory> value = (indexSettings, environment, name,
				settings) -> new SerbianTokenFilterFactory(indexSettings, name, settings);
		return Collections.singletonMap(FILTER_NAME, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
		AnalysisProvider<AnalyzerProvider<? extends Analyzer>> value = (indexSettings, environment, name,
				settings) -> new SerbianAnalyzerProvider(indexSettings, name, settings);
		return Collections.singletonMap(ANALYZER_NAME, value);
	}
}
