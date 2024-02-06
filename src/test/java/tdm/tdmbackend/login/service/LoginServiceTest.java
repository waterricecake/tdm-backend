package tdm.tdmbackend.login.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import tdm.tdmbackend.global.DtoCreater;
import tdm.tdmbackend.global.ServiceTest;
import tdm.tdmbackend.login.domain.MemberToken;
import tdm.tdmbackend.login.domain.RefreshToken;
import tdm.tdmbackend.login.dto.request.LoginRequest;
import tdm.tdmbackend.login.repository.RefreshTokenRepository;
import tdm.tdmbackend.login.util.JwtManager;

@Import({LoginService.class, JwtManager.class})
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
