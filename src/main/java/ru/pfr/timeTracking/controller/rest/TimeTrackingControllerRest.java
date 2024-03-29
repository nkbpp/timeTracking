package ru.pfr.timeTracking.controller.rest;

import lombok.RequiredArgsConstructor;
import org.opfr.springbootstarterauthsso.security.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.pfr.timeTracking.aop.valid.ValidError;
import ru.pfr.timeTracking.model.acs.entity.AccountSpecification;
import ru.pfr.timeTracking.model.timeTracking.dto.DateSPoDto;
import ru.pfr.timeTracking.model.timeTracking.dto.TimesOfDayDto;
import ru.pfr.timeTracking.model.timeTracking.dto.UUIDDto;
import ru.pfr.timeTracking.model.timeTracking.dto.WorkStatusDto;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeParam;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeParamSpecification;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeTracking;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeTrackingSpecification;
import ru.pfr.timeTracking.model.timeTracking.mapper.TimeTrackingMapper;
import ru.pfr.timeTracking.service.acs.AccountsService;
import ru.pfr.timeTracking.service.timeTracking.TimeParamService;
import ru.pfr.timeTracking.service.timeTracking.TimeTrackingService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/timeTracker"})
//@Validated //включить проверку как параметров запроса, так и переменных пути в наших контроллерах
public class TimeTrackingControllerRest {

    private final AccountsService accountsService;
    private final TimeTrackingService timeTrackingService;
    private final TimeTrackingMapper timeTrackingMapper;
    private final TimeParamService timeParamService;

    /**
     * Установить статус
     */
    @PutMapping(value = "/status/{stat}/{id}")
    public ResponseEntity<?> addBusinessTrip(
            @Valid WorkStatusDto workStatus,
            @Valid UUIDDto uuid) {
        try {

            String stat = workStatus.getStat();
            TimeTracking timeTracking = timeTrackingService.findOne(
                    TimeTrackingSpecification.idEqual(uuid.getId())
            ).orElseThrow();

            timeTracking.setBusinessTrip(stat.equals("businessTrip"));
            timeTracking.setSickLeave(stat.equals("sickLeave"));
            timeTracking.setVacation(stat.equals("vacation"));

            timeTrackingService.save(timeTracking);

            return ResponseEntity.ok(stat);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /**
     * Утренняя и вечерняя отметка
     */
    @PutMapping(value = "/timesOfDay/{timesOfDay}/{id}")
    public ResponseEntity<?> addMorning(
            @Valid TimesOfDayDto timesOfDay,
            @Valid UUIDDto uuid,
            @AuthenticationPrincipal UserInfo user) {
        try {

            TimeTracking timeTracking = timeTrackingService.findOne(
                    TimeTrackingSpecification.idEqual(uuid.getId())
            ).orElseThrow();

            LocalDateTime currentDate = LocalDateTime.now();

            TimeParam timeParam = timeParamService.findOne(TimeParamSpecification.codeEqual(user.getOrgCode())).orElse(null);

            //получить контрольное время
            LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.of(timeParam.getMornBegin().getHour(), timeParam.getMornBegin().getMinute()));
            LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.of(timeParam.getMornEnd().getHour(), timeParam.getMornEnd().getMinute()));
            if (timesOfDay.getTimesOfDay().equals("evening")) {
                start = LocalDateTime.of(LocalDate.now(), LocalTime.of(timeParam.getEvnBegin().getHour(), timeParam.getEvnBegin().getMinute()));
                end = LocalDateTime.of(LocalDate.now(), LocalTime.of(timeParam.getEvnEnd().getHour(), timeParam.getEvnEnd().getMinute()));
            }

            timeTracking.setBusinessTrip(false);
            timeTracking.setSickLeave(false);
            timeTracking.setVacation(false);

            if (timesOfDay.getTimesOfDay().equals("morning") &&
                    timeTracking.getBeginningOfWork() == null && //попытка перезаписи
                    (
                            (currentDate.isAfter(start) ||
                                    currentDate.isEqual(start))
                                    &&
                                    (currentDate.isBefore(end) ||
                                            currentDate.isEqual(end))
                    ) //время не попадает в промежуток
            ) {
                timeTracking.setBeginningOfWork(currentDate);
                timeTrackingService.save(timeTracking);
            } else if (timesOfDay.getTimesOfDay().equals("evening") &&
                    timeTracking.getEndOfWork() == null && //попытка перезаписи
                    (
                            (currentDate.isAfter(start) ||
                                    currentDate.isEqual(start))
                                    &&
                                    (currentDate.isBefore(end) ||
                                            currentDate.isEqual(end))
                    ) //время не попадает в промежуток
            ) {
                timeTracking.setEndOfWork(currentDate);
                timeTrackingService.save(timeTracking);
            } else {
                timeTracking.setMachination(timeTracking.getMachination() + 1);
                timeTrackingService.save(timeTracking);
                return ResponseEntity.badRequest().body("Махинация предотвращена!");
            }

            String msg = timesOfDay.getTimesOfDay().equals("evening") ? "Вечерняя отметка" : "Утренняя отметка";
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /**
     * Получить
     */
    @PostMapping("/get")
    public ResponseEntity<?> getTimeTracking(
            @AuthenticationPrincipal UserInfo user
    ) {
        try {

            var account = accountsService.findOne(
                    AccountSpecification.findByLogin(user.getUsername())
            ).orElseThrow();

            String orgCode = account.getEmployees().getOrganizationEntity().getCODE();

            var currentDate = LocalDate.now();
            TimeTracking timeTracking;

            if (timeTrackingService.isPresent(account.getLOGIN_ACC(), currentDate)) {
                timeTracking = timeTrackingService.findOne(
                        TimeTrackingSpecification.now(account.getLOGIN_ACC(), currentDate)
                ).orElseThrow();
            } else {
                timeTracking = TimeTracking.builder()
                        .login(account.getLOGIN_ACC())
                        .orgCode(orgCode)
                        .fam(account.getEmployees().getFAM_EM())
                        .name(account.getEmployees().getNAM_EM())
                        .otch(account.getEmployees().getOTCH_EM())
                        .currentDate(currentDate)
                        //отпуск
                        .vacation(
                                (
                                        account.getEmployees().getVACATION_START_DATE() != null &&
                                                (currentDate.isAfter(account.getEmployees().getVACATION_START_DATE()) ||
                                                        currentDate.isEqual(account.getEmployees().getVACATION_START_DATE()))
                                )
                                        &&
                                        (
                                                account.getEmployees().getVACATION_END_DATE() != null &&
                                                        (currentDate.isBefore(account.getEmployees().getVACATION_END_DATE()) ||
                                                                currentDate.isEqual(account.getEmployees().getVACATION_END_DATE()))
                                        )
                        )
                        .build();
                timeTrackingService.save(timeTracking);
            }

            return new ResponseEntity<>(
                    timeTrackingMapper.toDto(timeTracking),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /***
     * Установить командировку, больничный, отпуск
     */
    @ValidError
    @PostMapping("/status/period/{stat}")
    public ResponseEntity<?> editPeriod(
            @Valid WorkStatusDto stat,
            //@Valid UUIDDto id,
            @Valid @RequestBody DateSPoDto dateSPoDto,
            @AuthenticationPrincipal UserInfo user,
            Errors errors
    ) {
        try {

            var account = accountsService.findOne(
                    AccountSpecification.findByLogin(user.getUsername())
            ).orElseThrow();

            timeTrackingService.save(stat, dateSPoDto, account);

            return new ResponseEntity<>(
                    "",
                    HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

}
