package com.emploify.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String education;
    private MultipartFile cv;
    private MultipartFile coverLetter;
}
