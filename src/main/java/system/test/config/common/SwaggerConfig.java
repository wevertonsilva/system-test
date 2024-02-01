package system.test.config.common;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private static final String VERSION = "1.0";

    @Bean
    public OpenAPI customOpenAPI() {
        final String name = "API System Test";

        return new OpenAPI()
                .info(new Info()
                        .title(name)
                        .version(VERSION)
                        .summary(name));
    }

}