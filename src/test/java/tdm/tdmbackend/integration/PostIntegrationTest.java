package tdm.tdmbackend.integration;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import tdm.tdmbackend.global.DtoCreater;
import tdm.tdmbackend.global.IntegrationTest;
import tdm.tdmbackend.post.dto.request.PostRequest;

public class PostIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("게시물을 생성한다")
    void create() {
        // given
        final PostRequest request = DtoCreater.create(
                PostRequest.class,
                "testTitle",
                "testContent",
                List.of(1L, 2L, 3L),
                List.of("test.jpeg", "test.jpg")
        );

        // when
        final ExtractableResponse response = RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(request)
                .when().post("/posts")
                .then().log().all()
                .extract();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
                    softly.assertThat(response.header("Location")).contains("/posts/");
                }
        );
    }

    @Test
    @DisplayName("단일 게시물에 조회한다")
    void read() {
        // when
        final JsonPath response = RestAssured
                .given().log().all()
                .when().get("/posts/{postId}", 1L)
                .then().log().all()
                .extract()
                .body()
                .jsonPath();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.getLong("id")).isEqualTo(1L);
                    softly.assertThat(response.getString("title")).isEqualTo("test1");
                    softly.assertThat(response.getString("content")).isEqualTo("content");
                    softly.assertThat(response.getList("tags")).hasSize(3);
                    softly.assertThat(response.getList("images")).hasSize(2);
                    softly.assertThat(response.getMap("author")).containsEntry("id", 1);
                    softly.assertThat(response.getList("comments")).hasSize(2);
                }
        );
    }

    @Test
    @DisplayName("게시물을 수정한다")
    void update() {
        // given
        final PostRequest request = DtoCreater.create(
                PostRequest.class,
                "testTitle",
                "testContent",
                List.of(1L, 2L, 3L),
                List.of("test.jpeg", "test.jpg")
        );

        // when
        final ExtractableResponse response = RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(request)
                .when().put("/posts/{postId}", 1)
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("게시물을 삭제한다")
    void delete() {
        // when
        final ExtractableResponse response = RestAssured
                .given().log().all()
                .when().delete("/posts/{postId}", 1)
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
