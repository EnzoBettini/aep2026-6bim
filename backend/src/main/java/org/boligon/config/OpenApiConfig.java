package org.boligon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI observacaoOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("ObservaAção API")
                        .version("2.0")
                        .description(
                                "API REST para registro de problemas urbanos (ODS 11). "
                                        + "Coleções MongoDB: cidadaos e solicitacoes."
                        ));
    }
}
