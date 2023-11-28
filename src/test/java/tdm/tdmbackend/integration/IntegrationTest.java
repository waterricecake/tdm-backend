package tdm.tdmbackend.integration;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Sql(
        value = {
                "classpath:testdata/truncate.sql",
                "classpath:testdata/member.sql",
                "classpath:testdata/tag.sql",
                "classpath:testdata/post.sql",
                "classpath:testdata/post_tag.sql",
                "classpath:testdata/member_tag.sql",
                "classpath:testdata/image.sql"

        }
)
public abstract class IntegrationTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }
}
