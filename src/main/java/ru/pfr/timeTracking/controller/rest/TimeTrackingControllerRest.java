package ru.pfr.timeTracking.controller.rest;

import lombok.RequiredArgsConstructor;
import org.opfr.springbootstarterauthsso.security.UserInfo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pfr.timeTracking.aop.valid.ValidError;
import ru.pfr.timeTracking.model.acs.entity.AccountSpecification;
import ru.pfr.timeTracking.model.timeTracking.dto.TimeTrackingDto;
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
     * Добавить контракт
     */
    @ValidError
    @PostMapping
    public ResponseEntity<?> add(
            @AuthenticationPrincipal UserInfo user,
            @Valid @RequestBody TimeTrackingDto timeTracking) {
        try {

            //проход по документам
            /*List<DopDocuments> listDocuments = documents
                    .stream()
                    .map(documentsMapper::fromMultipart)
                    .filter(Objects::nonNull)
                    .toList();

            ContractAxo contract = contractAxoMapper
                    .fromDto(contractAxoDto);
            contract.setAllDocuments(listDocuments);
            contract.setUser(user);

            contractAxoService.save(contract);*/
            return ResponseEntity.ok("Данные добавлены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

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
                    TimeTrackingSpecification.idEqual(UUID.fromString(uuid.getId()))
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
            @Valid UUIDDto uuid) {
        try {

            TimeTracking timeTracking = timeTrackingService.findOne(
                    TimeTrackingSpecification.idEqual(UUID.fromString(uuid.getId()))
            ).orElseThrow();

            LocalDateTime currentDate = LocalDateTime.now();
            LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 59));
            LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 00));
            if (timesOfDay.getTimesOfDay().equals("evening")) {
                start = LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 59));
                end = LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 00));
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
     * Удалить контракт по id +?
     */
/*    @DeleteMapping("/deleteContract/{id}")
    public ResponseEntity<?> deleteContract(
            @PathVariable("id") Long id
    ) {
        try {
            contractAxoService.delete(id);
            return ResponseEntity.ok("Контракт с ID = " + id + " успешно удален!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }*/

    /**
     * Получить контракт если записи нет создать
     */
    @PostMapping("/get")
    public ResponseEntity<?> getContract(
            @AuthenticationPrincipal UserInfo user
    ) {
        try {

            var account = accountsService.findOne(
                    AccountSpecification.findByLogin(user.getUsername())
            ).orElseThrow();

            String orgCode = account.getEmployees().getOrganizationEntity().getCODE();
            if(!timeParamService.findOne(
                            TimeParamSpecification.codeEqual(orgCode)
                    )
                    .isPresent()){
                timeParamService.save(TimeParam.builder()
                                .orgCode(orgCode)
                        .build());
            }

            var currentDate = LocalDate.now();
            TimeTracking timeTracking = null;
            timeTracking = timeTrackingService.findOne(
                    TimeTrackingSpecification.now(
                            account.getLOGIN_ACC(), currentDate
                    )
            ).orElse(null);

            if (timeTracking == null) {
                timeTracking = TimeTracking.builder()
                        .login(account.getLOGIN_ACC())
                        .orgCode(orgCode)
                        .fam(account.getEmployees().getFAM_EM())
                        .name(account.getEmployees().getNAM_EM())
                        .otch(account.getEmployees().getOTCH_EM())
                        .currentDate(currentDate)
                        //отпуск
                        .vacation((currentDate.isAfter(account.getEmployees().getVACATION_START_DATE()) ||
                                currentDate.isEqual(account.getEmployees().getVACATION_START_DATE()))
                                &&
                                (currentDate.isBefore(account.getEmployees().getVACATION_END_DATE()) ||
                                        currentDate.isEqual(account.getEmployees().getVACATION_END_DATE())))
                        .build();
                timeTrackingService.save(timeTracking);
            }

            //timeTracking.setVacation(true);
            return new ResponseEntity<>(
                    timeTrackingMapper.toDto(timeTracking),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /**
     * Фильтр
     */
/*    @PostMapping("/findTable")
    public ResponseEntity<?> findTable(
            @RequestParam(defaultValue = "") String poleFindByNomGK,
            @RequestParam(defaultValue = "") String poleFindByKontragent,
            @RequestParam(defaultValue = "1") Integer param,
            @RequestParam(defaultValue = "10") Integer col
    ) {

        try {

            Sort sort = Sort.by(ContractAxo_.ID).descending();

            List<ContractAxo> contracts = contractAxoService.findAll(
                    ContractAxoSpecification.filterContractAXO(
                            poleFindByNomGK,
                            poleFindByKontragent
                    ),
                    PageRequest.of(
                            param - 1, col,
                            sort
                    )
            );

            return new ResponseEntity<>(
                    (contracts == null) ? null : contracts
                            .stream()
                            .map(contractAxoMapper::toDto)
                            .toList(),
                    HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }*/


}
