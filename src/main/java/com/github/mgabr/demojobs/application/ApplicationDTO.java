package com.github.mgabr.demojobs.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {

    private String candidateId;
    private String JobId;
    private String companyId;

    private String candidateName;
    private String jobName;
    private String companyName;
}
