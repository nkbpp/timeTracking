package ru.pfr.timeTracking.config.security;

import org.opfr.springbootstarterauthsso.security.UserInfo;
import org.opfr.springbootstarterauthsso.security.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;

public class WithMockCustomUserSecurityContextFactory
        implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserInfo principal =
                new UserInfo(
                        customUser.login(),
                        Arrays.stream(customUser.roles())
                                .map(UserRole::new)
                                .toList(),
                        "",
                        ""
                );

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        principal,
                        "password",
                        principal.getAuthorities()
                );
        context.setAuthentication(auth);

        return context;
    }
}
