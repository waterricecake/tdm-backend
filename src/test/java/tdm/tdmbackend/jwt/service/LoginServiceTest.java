package tdm.tdmbackend.jwt.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import tdm.tdmbackend.global.DtoCreater;
import tdm.tdmbackend.global.ServiceTest;
import tdm.tdmbackend.jwt.domain.MemberToken;
import tdm.tdmbackend.jwt.domain.RefreshToken;
import tdm.tdmbackend.jwt.dto.request.LoginRequest;
import tdm.tdmbackend.jwt.repository.RefreshTokenRepository;
import tdm.tdmbackend.jwt.util.JwtProvider;

@Import({LoginService.class, JwtProvider.class})
class LoginServiceTest extends ServiceTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @DisplayName("기존 유저가 로그인을 한다")
    @Test
    void login() {
        // given
        final LoginRequest request = DtoCreater.create(
                LoginRequest.class,
                "socialId",
                "nickname",
                "profile"
        );

        // when
        MemberToken memberToken = loginService.login(request);
        final List<String> refreshTokens = refreshTokenRepository
                .findAll()
                .stream()
                .map(RefreshToken::getRefreshToken)
                .toList();

        // then
        assertThat(refreshTokens).contains(memberToken.getRefreshToken());
    }
}
