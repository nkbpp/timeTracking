package ru.pfr.timeTracking.model.timeTracking.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class TimesOfDayDto {
    @Pattern(regexp = "^(morning)|(evening)$",
    message = "Invalid timesOfDay")
    String timesOfDay;
}
