package com.auctionhive.apigateway.filter;

import com.auctionhive.apigateway.filter.CustomJwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomJwtFilter customJwtFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                // 🚫 ABSOLUTELY DISABLE BASIC AUTH
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(auth -> auth

                        // ✅ FULL SWAGGER BYPASS
                        .pathMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-ui/index.html",
                                "/v3/api-docs/**",
                                "/user-service/v3/api-docs",
                                "/auction-service/v3/api-docs",
                                "/bid-service/v3/api-docs",
                                "/webjars/**"
                        ).permitAll()

                        // ---------- USER SERVICE ----------
                        .pathMatchers("/api/users/login", "/api/users/register").permitAll()
                        .pathMatchers("/api/admin/**").hasRole("ADMIN")
                        .pathMatchers("/api/seller/**").hasRole("SELLER")
                        .pathMatchers("/api/buyer/**").hasRole("BUYER")

                        // ---------- AUCTION SERVICE ----------
                        .pathMatchers("/api/auctions/**").permitAll()
                        .pathMatchers("/api/seller/auctions/**").hasRole("SELLER")
                        .pathMatchers("/api/admin/auctions/**").hasRole("ADMIN")

                        // ---------- BID SERVICE ----------
                        .pathMatchers("/api/buyer/bids/**").hasRole("BUYER")
                        .pathMatchers("/api/bids/**").permitAll()

                        // ---------- EVERYTHING ELSE ----------
                        .anyExchange().authenticated()
                )

                // ✅ JWT FILTER ONLY (NO BASIC AUTH)
                .addFilterAt(customJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)

                .build();
    }
}
