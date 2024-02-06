package tdm.tdmbackend.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import tdm.tdmbackend.global.DtoCreater;
import tdm.tdmbackend.global.IntegrationTest;
import tdm.tdmbackend.post.dto.request.CommentRequest;

public class CommentIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("댓글을 작성한다")
    void create() {
        // given
        final CommentRequest commentRequest = DtoCreater.create(
                CommentRequest.class,
                "content"
        );
        final String uri = "/comments/posts/" + 1L;

        // when
        final ExtractableResponse response = requestLogin()
                .body(commentRequest)
                .when().post(uri)
                .then().log().all()
                .extract();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
                    softly.assertThat(response.header("Location")).contains("/comments/");
                }
        );
    }

    @Test
    @DisplayName("댓글을 수정한다")
    void update() {
        // given
        final CommentRequest commentRequest = DtoCreater.create(
                CommentRequest.class,
                "updateContent"
        );
        final String uri = "/comments/" + 1L;

        // when
        final JsonPath response = requestLogin()
                .body(commentRequest)
                .when().put(uri)
                .then().log().all()
                .extract()
                .jsonPath();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.getLong("id")).isEqualTo(1L);
                    softly.assertThat(response.getString("content")).isEqualTo(commentRequest.getContent());
                    softly.assertThat(response.getMap("author")).containsEntry("id", 1);
                }
        );
    }

    @Test
    @DisplayName("댓글 삭제")
    void delete() {
        // given
        final String uri = "/comments/" + 1L;

        // when
        final ExtractableResponse response = requestLogin()
                .given().log().all()
                .when().delete(uri)
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
