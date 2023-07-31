package media.wepg.prototype.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("v1.0")
                .title("WEPG Prototype API Test")
                .description("WEPG Prototype API들을 테스트하는 Swagger UI 페이지입니다.");

        return new OpenAPI().info(info);
    }
}

