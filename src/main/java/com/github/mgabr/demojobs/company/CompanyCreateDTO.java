package com.github.mgabr.demojobs.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCreateDTO {

    private String email;
    private String password;
    private String name;
    private String description;
}
