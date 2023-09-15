package ru.pfr.timeTracking.controller.html;

import lombok.RequiredArgsConstructor;
import org.opfr.springbootstarterauthsso.security.UserInfo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.timeTracking.model.acs.entity.AccountSpecification;
import ru.pfr.timeTracking.model.acs.mapper.AccountsMapper;
import ru.pfr.timeTracking.model.timeTracking.dto.UUIDDto;
import ru.pfr.timeTracking.model.timeTracking.dto.WorkStatusDto;
import ru.pfr.timeTracking.service.acs.AccountsService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/boss"})
public class BossController {

    private final AccountsService accountsService;
    private final AccountsMapper accountsMapper;

    @GetMapping
    public String startIndex(
    ) {
        return "view/bossStat";
    }

    @GetMapping("/edit/{stat}")
    public String editVacation(
            @Valid WorkStatusDto stat,
            @AuthenticationPrincipal UserInfo user,
            Model model
    ) {
        model.addAttribute("stat", stat.getStat());
        model.addAttribute("accounts",
                accountsService.findAll(
                        AccountSpecification.findAllOrganizationKod(user.getOrgCode())
                ).stream()
                        .map(accountsMapper::toDto)
                        .toList()
        );
        return "view/bossEdit/stat";
    }

}
