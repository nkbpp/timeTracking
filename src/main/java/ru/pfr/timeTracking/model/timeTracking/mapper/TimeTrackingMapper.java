package ru.pfr.timeTracking.model.timeTracking.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.timeTracking.model.timeTracking.dto.TimeTrackingDto;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeTracking;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TimeTrackingMapper {

    public TimeTrackingDto toDto(TimeTracking obj) {
        return TimeTrackingDto.builder()
                .id(obj.getId().toString())
                .login(obj.getLogin())
                .currentDate(obj.getCurrentDate())
                .fam(obj.getFam())
                .name(obj.getName())
                .otch(obj.getOtch())
                .beginningOfWork(obj.getBeginningOfWork())
                .endOfWork(obj.getEndOfWork())
                .businessTrip(obj.getBusinessTrip())
                .vacation(obj.getVacation())
                .sickLeave(obj.getSickLeave())
                .inicial(obj.getFam() + " " +
                        obj.getName().charAt(0) + ". " +
                        ((obj.getOtch() !=null && obj.getOtch().length()>0) ? obj.getOtch().charAt(0) + "." : ""))
                .build();
    }

    public TimeTracking fromDto(TimeTrackingDto dto) {
        return TimeTracking.builder()
                .id(UUID.fromString(dto.getId()))
                .login(dto.getLogin())
                .currentDate(dto.getCurrentDate())
                .fam(dto.getFam())
                .name(dto.getName())
                .otch(dto.getOtch())
                .beginningOfWork(dto.getBeginningOfWork())
                .endOfWork(dto.getEndOfWork())
                .businessTrip(dto.getBusinessTrip())
                .vacation(dto.getVacation())
                .sickLeave(dto.getSickLeave())
                .build();
    }

}
