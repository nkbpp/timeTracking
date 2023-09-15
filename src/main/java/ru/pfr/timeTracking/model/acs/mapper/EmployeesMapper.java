package ru.pfr.timeTracking.model.acs.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.timeTracking.model.acs.dto.EmployeesDto;
import ru.pfr.timeTracking.model.acs.dto.ResDto;
import ru.pfr.timeTracking.model.acs.entity.Employees;
import ru.pfr.timeTracking.model.acs.entity.Res;

@Component
@RequiredArgsConstructor
public class EmployeesMapper {

    private final PostsMapper postsMapper;
    private final OrganizationEntityMapper organizationEntityMapper;

    public EmployeesDto toDto(Employees obj) {
        return EmployeesDto.builder()
                .id(obj.getID_EM())
                .fam(obj.getFAM_EM())
                .name(obj.getNAM_EM())
                .otch(obj.getOTCH_EM())
                .initials(
                        obj.getFAM_EM().substring(0, 1).toUpperCase() +
                                obj.getFAM_EM().substring(1).toLowerCase() + " " +
                                obj.getNAM_EM().substring(0, 1).toUpperCase() + "." +
                                obj.getOTCH_EM().substring(0, 1).toUpperCase() + "."
                )
                .dismissalDate(obj.getDISMISSAL_DATE_EM())
                .vacationStartDate(obj.getVACATION_START_DATE())
                .vacationEndDate(obj.getVACATION_END_DATE())
                .posts(obj.getPosts() == null ? null : postsMapper.toDto(obj.getPosts()))
                .organizationEntity(
                        obj.getOrganizationEntity() == null ?
                                null :
                                organizationEntityMapper.toDto(obj.getOrganizationEntity()))
                .build();
    }

    public Employees fromDto(EmployeesDto dto) {
        return Employees.builder()
                .ID_EM(dto.getId())
                .FAM_EM(dto.getFam())
                .NAM_EM(dto.getName())
                .OTCH_EM(dto.getOtch())
                .DISMISSAL_DATE_EM(dto.getDismissalDate())
                .VACATION_START_DATE(dto.getVacationStartDate())
                .VACATION_END_DATE(dto.getVacationEndDate())
                .posts(dto.getPosts() == null ? null : postsMapper.fromDto(dto.getPosts()))
                .organizationEntity(
                        dto.getOrganizationEntity() == null ?
                                null :
                                organizationEntityMapper.fromDto(dto.getOrganizationEntity()))
                .build();
    }

}
