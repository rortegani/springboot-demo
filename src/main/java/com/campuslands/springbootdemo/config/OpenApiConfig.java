package com.campuslands.springbootdemo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI/Swagger para exponer metadatos de la API.
 */
@Configuration
public class OpenApiConfig {
    
    /**
     * Construye el objeto OpenAPI con título, versión y servidor por defecto.
     * @return instancia configurada de {@link io.swagger.v3.oas.models.OpenAPI}
     */
    @Bean
    public OpenAPI api() {
    return new OpenAPI()
      .info(new Info()
        .title("Springboot Demo API")
        .version("1.0.0")
        .description("API para gestión de Categorías y Productos"))
      .addServersItem(new Server().url("http://localhost:8080"));
  }
}
