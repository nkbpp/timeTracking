package ru.pfr.timeTracking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.timeTracking.model.acs.entity.AccountSpecification;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeTracking;
import ru.pfr.timeTracking.repository.timeTracking.TimeTrackingRepository;
import ru.pfr.timeTracking.service.acs.AccountsService;
import ru.pfr.timeTracking.service.timeTracking.TimeTrackingService;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Component
public class TimeTrackingAndAcsService {

    private final TimeTrackingService timeTrackingService;
    private final AccountsService accountsService;

    @Scheduled(fixedDelay = 3600000) //1 час
    //@Scheduled(fixedDelay = 1000)
    public void checkVacation() {
        var accounts = accountsService.findAll(
                AccountSpecification.findAll()
        );

        final var currentDate = LocalDate.now();

        accounts.forEach(
                account -> {
                    if (!timeTrackingService.isPresent(account.getLOGIN_ACC(), currentDate)) {
                        timeTrackingService.save(
                                TimeTracking.builder()
                                        .login(account.getLOGIN_ACC())
                                        .orgCode(account.getEmployees().getOrganizationEntity().getCODE())
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
                                        .build()
                        );
                    }
                }
        );


    }

}
