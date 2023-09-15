package ru.pfr.timeTracking.model.acs.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.pfr.timeTracking.model.timeTracking.annotations.deserializer.CustomLocalDateDeserializerRuAndEn;
import ru.pfr.timeTracking.model.timeTracking.annotations.serializer.CustomLocalDateSerializerRu;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeesDto {
    private Long id;

    private String fam;
    private String name;
    private String otch;
    private String initials;

    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEn.class)
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    private LocalDate dismissalDate; //дата увольнения

    private PostsDto posts;

    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEn.class)
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    private LocalDate vacationEndDate;
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEn.class)
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    private LocalDate vacationStartDate;

    private OrganizationEntityDto organizationEntity;
}
