package ru.pfr.timeTracking.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/index", "/", ""})
public class StartController {

/*    private final AccountsService accountsService;


    @GetMapping
    public String startIndex(
            @AuthenticationPrincipal UserInfo user
    ) {
        var t = accountsService.findAll(
                AccountSpecificationTimeTracking.findAllOrganizationKod(user.getOrgCode())
        );

        return "index";
    }*/

}
