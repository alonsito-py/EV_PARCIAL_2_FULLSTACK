package com.urbano.pago.config;

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
                .title("Microservicio Pago")
                .description("Procesamiento de transacciones de pago")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Tiendas Urbano")
                    .email("dev@urbano.cl")));
    }
}
