package com.employify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoolQueryDTO {
    private String firstField;
    private String secondField;
    private String firstValue;
    private String secondValue;
    @JsonProperty
    private boolean isOr;
}
