package tdm.tdmbackend.global;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
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

    // todo: 인가 처리
    protected Member member;

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }

    @BeforeEach
    void setMember(){
        member = memberRepository.findById(1L)
                .orElseThrow();
    }
}
