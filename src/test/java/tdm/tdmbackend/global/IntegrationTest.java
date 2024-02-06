package tdm.tdmbackend.global;

import static io.restassured.http.ContentType.JSON;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import tdm.tdmbackend.login.dto.request.LoginRequest;
import tdm.tdmbackend.login.repository.RefreshTokenRepository;
import tdm.tdmbackend.member.domain.Member;
import tdm.tdmbackend.member.repository.MemberRepository;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Sql(
        value = {
                "classpath:testdata/truncate.sql",
                "classpath:testdata/member.sql",
                "classpath:testdata/tag.sql",
                "classpath:testdata/post.sql",
                "classpath:testdata/post_tag.sql",
                "classpath:testdata/member_tag.sql",
                "classpath:testdata/image.sql",
                "classpath:testdata/comment.sql"

        }
)
public abstract class IntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }

    protected RequestSpecification requestLogin() {
        Member member = memberRepository.findById(1L)
                .orElseThrow();
        LoginRequest request = DtoCreater.create(
                LoginRequest.class,
                member.getSocialId(),
                member.getNickname(),
                member.getProfile()
        );

        // when
        final ExtractableResponse response = RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(request)
                .when().post("/auth/login")
                .then().log().all()
                .extract();
        String accessToken = response.body().jsonPath().getString("accessToken");
        String refreshToken = (String) response.cookies().get("refresh-token");

        return RestAssured
                .given().log().all()
                .cookie("refresh-token", refreshToken)
                .headers(
                        "Authorization",
                        "Bearer " + accessToken,
                        "Content-Type",
                        JSON,
                        "Accept",
                        JSON)
                .contentType(JSON);
    }
}
