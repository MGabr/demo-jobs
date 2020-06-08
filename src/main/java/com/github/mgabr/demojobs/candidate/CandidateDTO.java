package com.github.mgabr.demojobs.candidate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDTO {

    private String email;
    private String name;
    private String description;
    private List<WorkExperienceDTO> workExperience = new ArrayList<>();
    private List<EducationDTO> education = new ArrayList<>();
}
