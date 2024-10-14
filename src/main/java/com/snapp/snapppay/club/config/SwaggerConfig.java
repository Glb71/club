package com.snapp.snapppay.club.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("snap-pay-club")
                .packagesToScan("com.snapp.snapppay.club")
                .addOpenApiCustomizer(openApi -> openApi.info(getApiInfo()))
                .build();
    }

    private Info getApiInfo() {
        return new Info()
                .title("Snap Pay Club REST API")
                .description("Snap Pay REST API Documentation");
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(getApiInfo())
                .addSecurityItem(new SecurityRequirement().addList("jwtAuth"))
                .components(new Components()
                        .addSecuritySchemes("jwtAuth", new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

}
