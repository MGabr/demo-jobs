package com.github.mgabr.demojobs.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationMessageDTO {

    private String message;
    private LocalDateTime sentAt;
    private String sentFromId;
}
