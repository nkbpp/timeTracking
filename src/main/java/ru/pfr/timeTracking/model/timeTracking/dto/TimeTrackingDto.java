package ru.pfr.timeTracking.model.timeTracking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.pfr.timeTracking.model.timeTracking.annotations.deserializer.CustomLocalDateDeserializerRuAndEnOrNull;
import ru.pfr.timeTracking.model.timeTracking.annotations.deserializer.CustomLocalDateTimeDeserializerRuAndEnOrNull;
import ru.pfr.timeTracking.model.timeTracking.annotations.serializer.CustomLocalDateSerializerRu;
import ru.pfr.timeTracking.model.timeTracking.annotations.serializer.CustomLocalDateTimeSerializerRu;
import ru.pfr.timeTracking.model.timeTracking.annotations.serializer.CustomLocalDateTimeSerializerRuTime;
import ru.pfr.timeTracking.model.timeTracking.annotations.valid.OrgCodeValid;
import ru.pfr.timeTracking.model.timeTracking.annotations.valid.UUIDValid;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeTrackingDto {

    @UUIDValid
    private String id;

    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    private LocalDate currentDate;

    @OrgCodeValid
    private String OrgCode;

    @NotBlank
    private String login;
    @NotBlank
    private String fam;
    @NotBlank
    private String name;

    private String inicial;

    private String otch;

    //Статусы
    @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializerRuTime.class)
    private LocalDateTime beginningOfWork;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializerRuTime.class)
    private LocalDateTime endOfWork;

    private Boolean businessTrip = false; // командировка
    private Boolean vacation = false; // отпуск
    private Boolean sickLeave = false; // больничный

}
