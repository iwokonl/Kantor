package org.example.currencies.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class IpAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final String gatewayIp;


    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        String requestIp = request.getRemoteAddr();
        boolean granted = gatewayIp.equals(requestIp);
        return new AuthorizationDecision(granted);
    }
}