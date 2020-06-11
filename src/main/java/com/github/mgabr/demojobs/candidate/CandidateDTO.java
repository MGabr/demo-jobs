package com.github.mgabr.demojobs.candidate;

import com.github.mgabr.demojobs.common.IdDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDTO implements IdDTO {

    private String id;
    private String name;
    private String description;
    private List<WorkExperienceDTO> workExperience = new ArrayList<>();
    private List<EducationDTO> education = new ArrayList<>();
}
