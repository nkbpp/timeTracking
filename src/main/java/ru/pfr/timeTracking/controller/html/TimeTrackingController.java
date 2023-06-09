package ru.pfr.timeTracking.controller.html;

import lombok.RequiredArgsConstructor;
import org.opfr.springbootstarterauthsso.security.UserInfo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/", ""})
public class TimeTrackingController {




    @GetMapping
    public String startIndex(
            @AuthenticationPrincipal UserInfo user
    ) {

/*        var t = accountsService.findAll(
                AccountSpecificationTimeTracking.findAllOrganizationKod(user.getOrgCode())
        );*/

        return "index";
    }

}
