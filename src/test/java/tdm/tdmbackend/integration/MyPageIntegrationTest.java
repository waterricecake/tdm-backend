package tdm.tdmbackend.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import tdm.tdmbackend.global.DtoCreater;
import tdm.tdmbackend.global.IntegrationTest;
import tdm.tdmbackend.member.dto.request.InterestRequest;
import tdm.tdmbackend.member.dto.request.SchoolRequest;

class MyPageIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("내 페이지에 조회한다")
    void getMyPage() {
        // when
        final JsonPath response = requestLogin()
                .when().get("/mypage")
                .then().log().all()
                .extract()
                .body()
                .jsonPath();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.getLong("id")).isEqualTo(1L);
                    softly.assertThat(response.getString("profileUrl")).contains("https:", "profile.jpg");
                    softly.assertThat(response.getString("nickname")).isEqualTo("name");
                    softly.assertThat(response.getString("school")).isEqualTo("school");
                    softly.assertThat(response.getLong("level")).isEqualTo(1L);
                    softly.assertThat(response.getList("interests")).hasSize(7);
                    softly.assertThat(response.getList("posts")).hasSize(6);
                }
        );
    }

    @Test
    @DisplayName("학교 정보 및 학년 정보 수정한다")
    void updateSchool() {
        // given
        final SchoolRequest request = DtoCreater.create(
                SchoolRequest.class,
                "updateSchool",
                2L
        );

        // when
        final ExtractableResponse response = requestLogin()
                .body(request)
                .when().put("/mypage/school")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("관심사를 수정한다")
    void updateInterest() {
        // given
        final InterestRequest request = DtoCreater.create(
                InterestRequest.class,
                List.of(1L, 2L)
        );

        // when
        final ExtractableResponse response = requestLogin()
                .body(request)
                .when().put("/mypage/interest")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
