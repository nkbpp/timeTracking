package ru.pfr.timeTracking.model.acs.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.timeTracking.model.acs.dto.ResDto;
import ru.pfr.timeTracking.model.acs.entity.Res;
import ru.pfr.timeTracking.model.timeTracking.dto.TimeTrackingDto;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeTracking;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ResMapper {

    public ResDto toDto(Res obj) {
        return ResDto.builder()
                .id(obj.getID_RES())
                .code(obj.getCODE_RES())
                .name(obj.getNAME_RES())
                .build();
    }

    public Res fromDto(ResDto dto) {
        return Res.builder()
                .ID_RES(dto.getId())
                .CODE_RES(dto.getCode())
                .NAME_RES(dto.getName())
                .build();
    }

}
