package com.auctionhive.apigateway.filter;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomJwtFilter implements WebFilter {

    private final JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        log.info("JWT FILTER -> path: {}", path);

        // =================== PUBLIC / SWAGGER BYPASS ===================
        if (isPublicPath(path)) {
            log.info("JWT FILTER -> BYPASS {}", path);
            return chain.filter(exchange);
        }
        // ===============================================================

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        // No token → let Spring Security decide (will 401 if required)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("JWT FILTER -> No Bearer token");
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtUtils.validateToken(token);

            String email = claims.getSubject();
            String role = claims.get("user_role", String.class);
            Long userId = claims.get("user_id", Long.class);

            // =================== FORWARD TRUSTED HEADERS ===================
            ServerHttpRequest mutatedRequest = exchange.getRequest()
                    .mutate()
                    .header("X-USER-ID", String.valueOf(userId))
                    .header("X-USER-ROLE", role)
                    .header("X-USER-EMAIL", email)
                    .build();

            ServerWebExchange mutatedExchange =
                    exchange.mutate().request(mutatedRequest).build();
            // ===============================================================

            List<GrantedAuthority> authorities =
                    List.of(new SimpleGrantedAuthority("ROLE_" + role));

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            authorities
                    );

            log.info("JWT FILTER -> AUTHENTICATED user={}, role={}", email, role);

            return chain.filter(mutatedExchange)
                    .contextWrite(
                            ReactiveSecurityContextHolder.withAuthentication(authentication)
                    );

        } catch (Exception ex) {
            log.error("JWT FILTER -> INVALID TOKEN", ex);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    // =================== SAFE PUBLIC PATH CHECK ===================
    private boolean isPublicPath(String path) {
        return path.startsWith("/api/users/login")
                || path.startsWith("/api/users/register")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/user-service/v3/api-docs")
                || path.startsWith("/auction-service/v3/api-docs")
                || path.startsWith("/webjars")
                || path.startsWith("/actuator");
    }
}
