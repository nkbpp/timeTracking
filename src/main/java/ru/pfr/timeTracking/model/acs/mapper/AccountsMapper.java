package ru.pfr.timeTracking.model.acs.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.timeTracking.model.acs.dto.AccountsDto;
import ru.pfr.timeTracking.model.acs.dto.EmployeesDto;
import ru.pfr.timeTracking.model.acs.entity.Accounts;
import ru.pfr.timeTracking.model.acs.entity.Employees;

@Component
@RequiredArgsConstructor
public class AccountsMapper {

    private final EmployeesMapper employeesMapper;
    private final ResMapper resMapper;

    public AccountsDto toDto(Accounts obj) {
        return AccountsDto.builder()
                .id(obj.getID_ACC())
                .employees(obj.getEmployees() == null ? null : employeesMapper.toDto(obj.getEmployees()))
                .res(obj.getRes() == null ? null : resMapper.toDto(obj.getRes()))
                .login(obj.getLOGIN_ACC())
                .build();
    }

    public Accounts fromDto(AccountsDto dto) {
        return Accounts.builder()
                .ID_ACC(dto.getId())
                .employees(dto.getEmployees() == null ? null : employeesMapper.fromDto(dto.getEmployees()))
                .res(dto.getRes() == null ? null : resMapper.fromDto(dto.getRes()))
                .LOGIN_ACC(dto.getLogin())
                .build();
    }

}
