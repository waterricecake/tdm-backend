package tdm.tdmbackend.integration;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tdm.tdmbackend.global.IntegrationTest;

class MyPageIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("내 페이지에 조회한다")
    void getMyPage() {
        // when
        final JsonPath response = RestAssured
                .given().log().all()
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
}
