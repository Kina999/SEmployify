package com.employify.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "agencija_za_zaposljavanje_dokumenti")
@Setting(settingPath = "/analyzerSettings/serbianAnalyzer.json")
public class IndexUnit {

    public static final String SERBIAN_ANALYZER = "serbian";

    @Id
    public String id;
    @Field(type = FieldType.Text, store = true, searchAnalyzer = SERBIAN_ANALYZER, analyzer = SERBIAN_ANALYZER)
    public String firstName;
    @Field(type = FieldType.Text, store = true, searchAnalyzer = SERBIAN_ANALYZER, analyzer = SERBIAN_ANALYZER)
    public String lastName;
//    @Field(type = FieldType.Double, store = true, searchAnalyzer = SERBIAN_ANALYZER, analyzer = SERBIAN_ANALYZER)
//    public double longitude;
//    @Field(type = FieldType.Double, store = true, searchAnalyzer = SERBIAN_ANALYZER, analyzer = SERBIAN_ANALYZER)
//    public double latitude;
    @GeoPointField
    public GeoPoint location;
    @Field(type = FieldType.Text, store = true, searchAnalyzer = SERBIAN_ANALYZER, analyzer = SERBIAN_ANALYZER)
    public String education;
    @Field(type = FieldType.Text, store = true, searchAnalyzer = SERBIAN_ANALYZER, analyzer = SERBIAN_ANALYZER)
    public String cvContent;
    @Field(type = FieldType.Text, store = true, searchAnalyzer = SERBIAN_ANALYZER, analyzer = SERBIAN_ANALYZER)
    public String cvPath;
    @Field(type = FieldType.Text, store = true, searchAnalyzer = SERBIAN_ANALYZER, analyzer = SERBIAN_ANALYZER)
    public String coverLetterContent;
    @Field(type = FieldType.Text, store = true, searchAnalyzer = SERBIAN_ANALYZER, analyzer = SERBIAN_ANALYZER)
    public String clPath;
}
