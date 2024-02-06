package tdm.tdmbackend.integration;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import tdm.tdmbackend.global.DtoCreater;
import tdm.tdmbackend.global.IntegrationTest;
import tdm.tdmbackend.login.dto.request.LoginRequest;

public class LoginIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("기존 유저 로그인")
    void login() {
        // given
        LoginRequest request = DtoCreater.create(
                LoginRequest.class,
                "socialId",
                "nickname",
                "profile"
        );

        // when
        final ValidatableResponse response = RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(request)
                .when().post("/login")
                .then().log().all();

        // then
        response
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("accessToken", notNullValue())
                .cookie("refresh-token", notNullValue())
                .extract();
    }

    @Test
    @DisplayName("신규 유저 로그인")
    void signUp(){
        // given
        LoginRequest request = DtoCreater.create(
                LoginRequest.class,
                "newUser",
                "nickname",
                "profile"
        );

        // when
        final ValidatableResponse response = RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(request)
                .when().post("/login")
                .then().log().all();

        // then
        response
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("accessToken", notNullValue())
                .cookie("refresh-token", notNullValue())
                .extract();
    }
}
