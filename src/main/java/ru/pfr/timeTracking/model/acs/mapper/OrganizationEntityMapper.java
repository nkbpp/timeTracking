package ru.pfr.timeTracking.model.acs.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.timeTracking.model.acs.dto.OrganizationEntityDto;
import ru.pfr.timeTracking.model.acs.dto.PostsDto;
import ru.pfr.timeTracking.model.acs.entity.OrganizationEntity;
import ru.pfr.timeTracking.model.acs.entity.Posts;

@Component
@RequiredArgsConstructor
public class OrganizationEntityMapper {

    public OrganizationEntityDto toDto(OrganizationEntity obj) {
        return OrganizationEntityDto.builder()
                .id(obj.getID())
                .code(obj.getCODE())
                .name(obj.getNAME())
                .fullName(obj.getFULL_NAME())
                .build();
    }

    public OrganizationEntity fromDto(OrganizationEntityDto dto) {
        return OrganizationEntity.builder()
                .ID(dto.getId())
                .CODE(dto.getCode())
                .NAME(dto.getName())
                .FULL_NAME(dto.getFullName())
                .build();
    }

}
