package ru.pfr.timeTracking.model.acs.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationEntityDto {
    private Long id;
    private String code;
    private String fullName;
    private String name;
}
