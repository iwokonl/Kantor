package org.example.currencies.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IpFilter extends OncePerRequestFilter {

    private final String gatewayIp;

    public IpFilter(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestIp = request.getRemoteAddr();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && !requestIp.equals(gatewayIp)) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
            return;
        }

        filterChain.doFilter(request, response);
    }
}