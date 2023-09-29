package ru.pfr.timeTracking.model.timeTracking.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class WorkStatusDto {
    @Pattern(regexp = "^(atwork)|(vacation)|(sickLeave)|(businessTrip)|(absence)$",
    message = "Invalid status")
    String stat;
}
