package arcn.roomfinder.booking.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = { @Server(url = "https://refactored-tribble-wrxg49pwwghvq45-8080.app.github.dev/") })
public class Swagger {
    @Bean
    OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Booking rooms")
                        .version("1.0.0")
                        .description("Hotel room booking API"));
    }
}
