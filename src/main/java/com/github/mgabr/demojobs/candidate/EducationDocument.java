package com.github.mgabr.demojobs.candidate;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;

import java.time.LocalDate;

@Data
@TypeAlias("education")
public class EducationDocument {

    private String degree;
    private String institution;
    private String description;
    private LocalDate from;
    private LocalDate to;
}
