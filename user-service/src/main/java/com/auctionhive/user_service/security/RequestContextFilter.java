package com.auctionhive.user_service.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("gatewayRequestContextFilter")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestContextFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            String userId = httpRequest.getHeader("X-USER-ID");
            String role   = httpRequest.getHeader("X-USER-ROLE");
            String email  = httpRequest.getHeader("X-USER-EMAIL");

            if (userId != null) {
                RequestContext.get().setUserId(Long.valueOf(userId));
            }
            RequestContext.get().setRole(role);
            RequestContext.get().setEmail(email);

            chain.doFilter(request, response);
        } finally {
            RequestContext.clear();
        }
    }
}
