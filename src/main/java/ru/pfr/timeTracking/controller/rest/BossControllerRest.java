package ru.pfr.timeTracking.controller.rest;

import lombok.RequiredArgsConstructor;
import org.opfr.springbootstarterauthsso.security.UserInfo;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pfr.timeTracking.aop.valid.ValidError;
import ru.pfr.timeTracking.model.timeTracking.dto.DateSPoDto;
import ru.pfr.timeTracking.model.timeTracking.dto.TimeTrackingDto;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeTracking;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeTrackingSpecification;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeTracking_;
import ru.pfr.timeTracking.model.timeTracking.mapper.TimeTrackingMapper;
import ru.pfr.timeTracking.service.timeTracking.TimeTrackingService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/boss"})
//@Validated //включить проверку как параметров запроса, так и переменных пути в наших контроллерах
public class BossControllerRest {

    private final TimeTrackingService timeTrackingService;
    private final TimeTrackingMapper timeTrackingMapper;

    /**
     * Фильтр по дате
     */
    @ValidError
    @PostMapping("/stat")
    public ResponseEntity<?> bossStatTable(
            @Valid @RequestBody DateSPoDto dateSPoDto,
            @AuthenticationPrincipal UserInfo user,
            Errors errors
    ) {

        try {
            List<TimeTracking> timeTracking = timeTrackingService.findAll(
                    TimeTrackingSpecification.getOtdelPeriod(
                            user.getOrgCode(),
                            dateSPoDto.getDateS(),
                            dateSPoDto.getDatePo()
                    ),
                    Sort.by(TimeTracking_.FAM).descending()
            );

            Map<String, List<TimeTrackingDto>> map = timeTracking
                    .stream()
                    .map(timeTrackingMapper::toDto).toList()
                    .stream()
                    .collect(Collectors.groupingBy(TimeTrackingDto::getInicial));

            return new ResponseEntity<>(
                    map,
                    HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }


}
