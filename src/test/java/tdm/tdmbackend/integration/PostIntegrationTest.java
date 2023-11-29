package tdm.tdmbackend.integration;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import tdm.tdmbackend.global.DtoCreater;
import tdm.tdmbackend.global.IntegrationTest;
import tdm.tdmbackend.post.dto.request.PostCreateRequest;

public class PostIntegrationTest extends IntegrationTest {

    @Test
    void create() {
        // given
        PostCreateRequest request = DtoCreater.create(
                PostCreateRequest.class,
                "testTitle",
                "testContent",
                List.of(1L, 2L, 3L),
                List.of("test.jpeg", "test.jpg")
        );

        // when
        ExtractableResponse response = RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(request)
                .when().post("/posts")
                .then()
                .log().all()
                .extract();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
                    softly.assertThat(response.header("Location")).contains("/posts/");
                }
        );
    }
}
