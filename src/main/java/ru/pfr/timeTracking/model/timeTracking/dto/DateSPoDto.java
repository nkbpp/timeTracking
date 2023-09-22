package ru.pfr.timeTracking.model.timeTracking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pfr.timeTracking.model.timeTracking.annotations.deserializer.CustomLocalDateDeserializerRuAndEn;
import ru.pfr.timeTracking.model.timeTracking.annotations.serializer.CustomLocalDateSerializerRu;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateSPoDto {
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEn.class)
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate dateS; //действие ГК с
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEn.class)
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate datePo; //действие ГК по
}
