package com.auctionhive.auction_service.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI auctionServiceOpenAPI() {

        final String securitySchemeName = "BearerAuth";

        return new OpenAPI()
                // 👇 IMPORTANT: FORCE GATEWAY URL
                .servers(List.of(
                        new Server().url("http://localhost:8080")
                ))

                .info(new Info()
                        .title("AuctionHive – Auction Service API")
                        .description("APIs for Auctions, Items, and Images")
                        .version("1.0.0"))

                .addSecurityItem(
                        new SecurityRequirement().addList(securitySchemeName))

                .components(
                        new io.swagger.v3.oas.models.Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
