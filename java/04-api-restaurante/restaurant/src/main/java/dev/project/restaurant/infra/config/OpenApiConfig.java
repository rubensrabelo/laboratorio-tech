package dev.project.restaurant.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI restaurantOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Restaurant API")
                        .description("REST API for restaurant management, table control, orders, and billing workflows.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Restaurant Support")
                                .email("support@restaurant.project.dev"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}
