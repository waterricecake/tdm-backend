package tdm.tdmbackend.global.config;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import tdm.tdmbackend.auth.Auth;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        name = AUTHORIZATION
)
@SecurityScheme(
        type = SecuritySchemeType.APIKEY,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.COOKIE,
        name = "refreshToken"
)
public class SwaggerConfig {

    @Value("${domain.url}")
    private String DOMAIN_URL;

    static {
        SpringDocUtils.getConfig()
                .addAnnotationsToIgnore(Auth.class)
                .addAnnotationsToIgnore(CookieValue.class)
                .addAnnotationsToIgnore(RequestHeader.class);
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .servers(setServer());
    }

    private Info apiInfo() {
        return new Info()
                .title("TDM API 문서")
                .description("TDM API 문서")
                .version("1.0.0");
    }

    private List<Server> setServer(){
        final Server httpsServer = new Server();
        httpsServer.setUrl(DOMAIN_URL);
        return List.of(httpsServer);
    }
}
