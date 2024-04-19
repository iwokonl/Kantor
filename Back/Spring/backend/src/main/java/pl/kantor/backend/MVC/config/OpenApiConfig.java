package pl.kantor.backend.MVC.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;



@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Iwo",
                        email = "iwo.stanislawski@icloud.com",
                        url = "https://www.linkedin.com/in/iwostanisławski/"
                ),
                title = "Kantor API",
                version = "1.0",
                description = "API for all applications in Kantor project",
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                ),
                termsOfService = "Zrobione przez Iwo Stanisławski dla projektu z zeto i dla koła naukowego WebLeader"
        ),
        servers = {
                @Server(
                        description = "Backend lokalny",
                        url = "http://localhost:8082/api"
                ),
                @Server(
                        description = "Frontend lokalny",
                        url = "http://localhost:4200"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
