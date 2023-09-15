package ru.pfr.timeTracking.model.acs.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResDto {
    private Long id;
    private String name;
    private Long code;
}
