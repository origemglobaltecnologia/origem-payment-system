package tech.origem.payment.auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Origem Auth Service API")
                        .version("1.0.0")
                        .description("Serviço de Identidade (IdP) para o ecossistema Origem Payment.")
                        .contact(new Contact()
                                .name("Suporte Técnico")
                                .email("tech@origem.tech")));
    }
}
