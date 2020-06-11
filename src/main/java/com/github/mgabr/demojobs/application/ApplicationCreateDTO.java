package com.github.mgabr.demojobs.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCreateDTO {

    private String candidateId;
    private String JobId;
}
