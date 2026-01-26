package com.auctionhive.bid_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component("auctionRequestContextFilter")
public class AuctionRequestContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String userId = request.getHeader("X-USER-ID");
            String role   = request.getHeader("X-USER-ROLE");
            String email  = request.getHeader("X-USER-EMAIL");

            if (userId != null) {
                RequestContext ctx = RequestContext.get();
                ctx.setUserId(Long.parseLong(userId));
                ctx.setRole(role);
                ctx.setEmail(email);

                log.debug("RequestContext set: userId={}, role={}", userId, role);
            }

            filterChain.doFilter(request, response);

        } finally {
            RequestContext.clear();
        }
    }
}
