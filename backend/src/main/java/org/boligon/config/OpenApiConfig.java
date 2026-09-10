package org.boligon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
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
                        .description("""
                                API do sistema ObservaAção para gestão de solicitações cidadãs.

                                Autenticação por sessão: faça login em POST /api/auth/login e use \
                                os endpoints protegidos na mesma sessão do navegador (cookie JSESSIONID).

                                Usuários padrão:
                                - Gestor: admin@admin.com / 123
                                - Cidadão: cidadao@test.com / 123
                                """)
                        .version("2.0")
                        .contact(new Contact()
                                .name("ObservaAção")
                                .email("admin@admin.com")));
    }
}
