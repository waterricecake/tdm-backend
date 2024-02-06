package tdm.tdmbackend.login.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import org.junit.jupiter.api.Test;
import tdm.tdmbackend.global.exception.AccessTokenException;
import tdm.tdmbackend.global.exception.RefreshTokenException;
import tdm.tdmbackend.login.domain.MemberToken;

class JwtManagerTest {

    private static final String SECRET_KEY = "secretkeysecretkeysecretkeysecretkeysecretkey!!!!";

    @Test
    void createMemberToken() {
        // given
        JwtManager jwtManager = new JwtManager(SECRET_KEY, 100000L, 1000000L);
        String expectedSubject = "123";

        // when
        MemberToken memberToken = jwtManager.createMemberToken(expectedSubject);

        // then
        assertThat(jwtManager.parseSubject(memberToken.getAccessToken())).isEqualTo(expectedSubject);
    }

    @Test
    void validateTokens() {
        // given
        JwtManager jwtManager = new JwtManager(SECRET_KEY, 100000L, 1000000L);
        MemberToken memberToken = jwtManager.createMemberToken("123");

        // when & then
        assertThatCode(() -> jwtManager.validateTokens(memberToken))
                .doesNotThrowAnyException();
    }

    @Test
    void validateTokens_AccessTokenExpired() {
        // given
        JwtManager jwtManager = new JwtManager(SECRET_KEY, 0L, 1000000L);
        MemberToken memberToken = jwtManager.createMemberToken("123");

        // when & then
        assertThatCode(() -> jwtManager.validateTokens(memberToken))
                .isInstanceOf(AccessTokenException.class);
    }

    @Test
    void validateTokens_RefreshTokenExpired() {
        // given
        JwtManager jwtManager = new JwtManager(SECRET_KEY, 1000000L, 0L);
        MemberToken memberToken = jwtManager.createMemberToken("123");

        // when & then
        assertThatCode(() -> jwtManager.validateTokens(memberToken))
                .isInstanceOf(RefreshTokenException.class);
    }
}
