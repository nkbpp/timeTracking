package ru.pfr.timeTracking.model.timeTracking.dto;

import lombok.Data;
import ru.pfr.timeTracking.model.timeTracking.annotations.valid.UUIDValid;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UUIDDto {
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
    message = "Invalid id")
    String id;
}
