package com.employify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponseDTO {
    private String firstName;
    private String lastName;
    private double longitude;
    private double latitude;
    private String education;
    private String cvPath;
    private String cvContent;
    private String clPath;
    private String clContent;
    private String highlight;
}
