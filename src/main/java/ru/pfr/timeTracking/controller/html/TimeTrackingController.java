package ru.pfr.timeTracking.controller.html;

import lombok.RequiredArgsConstructor;
import org.opfr.springbootstarterauthsso.security.UserInfo;
import org.opfr.springbootstarterauthsso.security.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.timeTracking.model.acs.entity.AccountSpecification;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeParam;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeParamSpecification;
import ru.pfr.timeTracking.service.acs.AccountsService;
import ru.pfr.timeTracking.service.timeTracking.TimeParamService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/", ""})
public class TimeTrackingController {

    private final AccountsService accountsService;
    private final TimeParamService timeParamService;

    @GetMapping
    public String startIndex(
            @AuthenticationPrincipal UserInfo user,
            Model model
    ) {

        var account = accountsService.findOne(
                AccountSpecification.findByLogin(user.getUsername())
        ).orElseThrow();

        var head = account.getEmployees().getPosts().getHEAD();
        //если начальник или его зам накинуть ролей
        if(head == 1 || head == 2 || user.getUsername().equals("041BratchinAV")){ //todo убрать 041BratchinAV
            //проверка что роли еще нет
            if (user.getRoleList().stream().noneMatch(userRole -> userRole.getAuthority().equals("ROLE_BOSS"))) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                var newRole = new UserRole("ROLE_BOSS");
                Set<GrantedAuthority> updatedAuthorities = new HashSet<>(auth.getAuthorities());
                updatedAuthorities.add(newRole);
                user.getRoleList().add(newRole);//new principal
                Authentication newAuth = new UsernamePasswordAuthenticationToken(user, auth.getCredentials(), updatedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(newAuth);
            }
        }

        String orgCode = user.getOrgCode();
        if (timeParamService.findOne(
                        TimeParamSpecification.codeEqual(orgCode)
                ).isEmpty()) {
            timeParamService.save(TimeParam.builder()
                    .orgCode(orgCode)
                    .build());
        }

        model.addAttribute("timeParam", timeParamService.findOne(
                        TimeParamSpecification.codeEqual(orgCode)
                ).orElse(null)
        );

        return "index";
    }

}
