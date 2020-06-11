package com.github.mgabr.demojobs.company;

import com.github.mgabr.demojobs.common.IdDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO implements IdDTO {

    private String id;
    private String name;
    private String description;
}
