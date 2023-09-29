package ru.pfr.timeTracking.service.timeTracking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.timeTracking.model.acs.entity.Accounts;
import ru.pfr.timeTracking.model.timeTracking.dto.DateSPoDto;
import ru.pfr.timeTracking.model.timeTracking.dto.TimeTrackingDto;
import ru.pfr.timeTracking.model.timeTracking.dto.WorkStatusDto;
import ru.pfr.timeTracking.model.timeTracking.entity.*;
import ru.pfr.timeTracking.model.timeTracking.mapper.TimeTrackingMapper;
import ru.pfr.timeTracking.repository.timeTracking.TimeTrackingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Transactional(transactionManager = "timetrackingTransactionManager")
public class TimeTrackingService {

    private final TimeTrackingRepository repository;
    private final TimeParamService timeParamService;
    private final TimeTrackingMapper timeTrackingMapper;

    public void update(TimeTracking carer) {
        repository.save(carer);
    }

    public void save(WorkStatusDto stat, DateSPoDto dateSPoDto, Accounts accounts) {
        for (LocalDate d = dateSPoDto.getDateS(); d.isBefore(dateSPoDto.getDatePo()) || d.isEqual(dateSPoDto.getDatePo()); d = d.plusDays(1L)) {
            TimeTracking newTimeTracking;
            if (isPresent(accounts.getLOGIN_ACC(), d)) {
                newTimeTracking = findOne(
                        TimeTrackingSpecification.now(accounts.getLOGIN_ACC(), d)
                ).orElseThrow();
                //отсутствие
                if(stat.getStat().equals("absence")){
                    newTimeTracking.setEndOfWork(null);
                    newTimeTracking.setBeginningOfWork(null);
                    newTimeTracking.setVacation(false);
                    newTimeTracking.setSickLeave(false);
                    newTimeTracking.setBusinessTrip(false);
                } else{
                    newTimeTracking.setVacation(stat.getStat().equals("vacation"));
                    newTimeTracking.setSickLeave(stat.getStat().equals("sickLeave"));
                    newTimeTracking.setBusinessTrip(stat.getStat().equals("businessTrip"));
                }
            } else {
                newTimeTracking = TimeTracking.builder()
                        .login(accounts.getLOGIN_ACC())
                        .orgCode(accounts.getEmployees().getOrganizationEntity().getCODE())
                        .fam(accounts.getEmployees().getFAM_EM())
                        .name(accounts.getEmployees().getNAM_EM())
                        .otch(accounts.getEmployees().getOTCH_EM())
                        .currentDate(d)
                        .vacation(stat.getStat().equals("vacation"))
                        .businessTrip(stat.getStat().equals("businessTrip"))
                        .sickLeave(stat.getStat().equals("sickLeave"))
                        .build();
            }
            save(newTimeTracking);
        }
    }

    public void save(TimeTracking carer) {
        repository.save(carer);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public List<TimeTracking> findAll(Specification<TimeTracking> specification) {
        return repository.findAll(specification);
    }

    public List<TimeTracking> findAll(Specification<TimeTracking> specification, Sort sort) {
        return repository.findAll(specification, sort);
    }

    public Optional<TimeTracking> findOne(Specification<TimeTracking> specification) {
        return repository.findOne(specification);
    }

    public boolean isPresent(String login, LocalDate date) {
        return repository.exists(
                TimeTrackingSpecification.now(
                        login, date
                )
        );
    }

    public void amnesty(DateSPoDto dateSPoDto, String login, String code) {
        for (LocalDate d = dateSPoDto.getDateS(); d.isBefore(dateSPoDto.getDatePo()) || d.isEqual(dateSPoDto.getDatePo()); d = d.plusDays(1L)) {
            TimeTracking timeTracking = findOne(
                    TimeTrackingSpecification.now(login, d)
            ).orElseThrow();

            LocalDateTime now = LocalDateTime.now();

            TimeParam timeParam = timeParamService.findOne(
                    TimeParamSpecification.codeEqual(code)
            ).orElseThrow();

            LocalDateTime beginningOfWork = timeParam.afterMorning(d, now) ?
                    LocalDateTime.of(
                            d,
                            LocalTime.of(timeParam.getMornBegin().getHour(), timeParam.getMornBegin().getMinute() + 1)
                    ) :
                    null;
            LocalDateTime endOfWork = timeParam.afterEvening(d, now) ?
                    LocalDateTime.of(
                            d,
                            LocalTime.of(timeParam.getEvnBegin().getHour(), timeParam.getEvnBegin().getMinute() + 1)
                    ) :
                    null;
            timeTracking.setBeginningOfWork(beginningOfWork);
            timeTracking.setEndOfWork(endOfWork);
            timeTracking.setBusinessTrip(false);
            timeTracking.setSickLeave(false);
            timeTracking.setVacation(false);
            save(timeTracking);
        }
    }

    public Map<String, List<TimeTrackingDto>> getTable(LocalDate dateS, LocalDate datePo, String otdel) {
        List<TimeTracking> timeTracking = findAll(
                TimeTrackingSpecification.getOtdelPeriod(
                        otdel,
                        dateS,
                        datePo
                ),
                Sort.by(TimeTracking_.FAM).descending()
                        .and(Sort.by(TimeTracking_.CURRENT_DATE))
        );

        return timeTracking
                .stream()
                .map(timeTrackingMapper::toDto).toList()
                .stream()
                .collect(
                        Collectors.groupingBy(TimeTrackingDto::getInicial)
                );
    }

}
