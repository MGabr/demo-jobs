package com.github.mgabr.demojobs.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationMessageCreateDTO {

    private String message;

    // TODO: sentFrom and sentTo from current user
    private String sentFromId;
    private String sentToId;
}
