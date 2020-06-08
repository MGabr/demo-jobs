package com.github.mgabr.demojobs.candidate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationDTO {

    private String degree;
    private String institution;
    private String description;
    private LocalDate from;
    private LocalDate to;
}
