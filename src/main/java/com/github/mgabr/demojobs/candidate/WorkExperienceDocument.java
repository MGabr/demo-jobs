package com.github.mgabr.demojobs.candidate;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;

import java.time.LocalDate;

@Data
@TypeAlias("workExperience")
public class WorkExperienceDocument {

    private String position;
    private String company;
    private String description;
    private LocalDate from;
    private LocalDate to;
}
