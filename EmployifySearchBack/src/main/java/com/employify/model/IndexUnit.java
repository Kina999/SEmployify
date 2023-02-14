package com.employify.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

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
    @Field(type = FieldType.Text, store = true, searchAnalyzer = SERBIAN_ANALYZER, analyzer = SERBIAN_ANALYZER)
    public String address;
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
