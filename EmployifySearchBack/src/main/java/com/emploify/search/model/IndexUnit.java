package com.emploify.search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "agencija_za_zaposljavanje_dokumenti")
public class IndexUnit {
    @Id
    public String id;
    public String firstName;
    public String lastName;
    public String address;
    public String education;
    public String cvContent;
    public String coverLetterContent;
}
