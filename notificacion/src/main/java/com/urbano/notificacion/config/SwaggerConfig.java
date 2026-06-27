package com.urbano.notificacion.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Microservicio Notificación")
                .description("Envío y registro de notificaciones a clientes")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Tiendas Urbano")
                    .email("dev@urbano.cl")));
    }
}
