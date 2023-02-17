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
    @Field(type = FieldType.Text, store = true)
    public String firstName;
    @Field(type = FieldType.Text, store = true)
    public String lastName;
    @GeoPointField
    public GeoPoint location;
    @Field(type = FieldType.Text, store = true)
    public String education;
    @Field(type = FieldType.Text, store = true)
    public String cvContent;
    @Field(type = FieldType.Text, store = true)
    public String cvPath;
    @Field(type = FieldType.Text, store = true)
    public String coverLetterContent;
    @Field(type = FieldType.Text, store = true)
    public String clPath;
}
