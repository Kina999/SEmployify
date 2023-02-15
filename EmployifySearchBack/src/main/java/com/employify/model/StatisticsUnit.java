package com.employify.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "logstashindex")
public class StatisticsUnit {

    @Id
    public String id;
    @Field(type = FieldType.Text)
    public String message;
}
