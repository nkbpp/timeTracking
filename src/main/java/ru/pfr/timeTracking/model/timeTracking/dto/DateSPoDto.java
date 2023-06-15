package ru.pfr.timeTracking.model.timeTracking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pfr.timeTracking.model.timeTracking.annotations.deserializer.CustomLocalDateDeserializerRuAndEn;
import ru.pfr.timeTracking.model.timeTracking.annotations.serializer.CustomLocalDateSerializerRu;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DateSPoDto {
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEn.class)
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    private LocalDate dateS; //действие ГК с
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEn.class)
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    private LocalDate datePo; //действие ГК по
}
