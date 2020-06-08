package com.github.mgabr.demojobs.candidate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceDTO {

    private String position;
    private String company;
    private String description;
    private LocalDate from;
    private LocalDate to;
}
