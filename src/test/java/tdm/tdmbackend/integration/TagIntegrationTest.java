package tdm.tdmbackend.integration;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tdm.tdmbackend.global.IntegrationTest;

public class TagIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("전체 태그를 조회한다")
    void getAllTags() {
        // when
        final JsonPath response = RestAssured
                .given().log().all()
                .when().get("/tags")
                .then().log().all()
                .extract()
                .jsonPath();

        // then
        assertThat(response.getList("tags")).hasSize(10);
    }
}
