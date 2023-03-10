/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.employify.indexer.analyzers;

import java.io.IOException;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;

/**
 * To set Analyzer on indexed entity field add annotation
 * 
 * @Field ( type = FieldType.String, index = FieldIndex.analyzed, store = true,
 *        analyzer="serbian_analyzer")
 */
@SuppressWarnings("rawtypes")
public class SerbianAnalyzerProvider extends AbstractIndexAnalyzerProvider {

	public static final String NAME = "serbian";
	private final SerbianAnalyzer analyzer;

	public SerbianAnalyzerProvider(IndexSettings indexSettings, String name, Settings settings) throws IOException {
		super(indexSettings, name, settings);
		analyzer = new SerbianAnalyzer();
//		analyzer.setVersion(org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider.version);
	}

	@Override
	public SerbianAnalyzer get() {
		return analyzer;
	}

}
