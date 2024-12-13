package com.indra.marketplace.shopping.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API", version = "v1", description = "Documentación de la API"))
public class OpenApiConfig {
}
