package pfw.gruppeG.MADN.common;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocumentationConfig {

    @Bean
    public OpenAPI openApiConfig() {

        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development Server environment");

        Contact jContact = new Contact();
        jContact.setName("Jannes Bierma");
        jContact.setEmail("jannes.bierma@stud.hs-emden-leer.de");



        Info info = new Info();
        info.setTitle("Mensch Ärgere Dich Nicht API");
        info.setDescription("API for the board game Mensch Ärgere Dich Nicht");
        info.setVersion("1.0");
        info.setContact(jContact);


        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("Bearer Authentication");

        Components component = new Components();
        component.addSecuritySchemes("Bearer Authentication", createAPIKeyScheme());

        return new OpenAPI()
                .info(info)
                .addServersItem(server)
                .addSecurityItem(securityRequirement)
                .components(component);
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

}
