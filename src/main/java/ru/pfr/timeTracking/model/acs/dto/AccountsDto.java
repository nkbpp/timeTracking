package ru.pfr.timeTracking.model.acs.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountsDto {
    private Long id;

    private EmployeesDto employees;

    private String login;

    private ResDto res;
}
