package tdm.tdmbackend.login.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

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
import tdm.tdmbackend.member.domain.Member;
import tdm.tdmbackend.member.repository.MemberRepository;

@Import({LoginService.class, JwtManager.class})
class LoginServiceTest extends ServiceTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private MemberRepository memberRepository;

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

    @DisplayName("신규 유저가 로그인한다")
    @Test
    void signUp(){
        // given
        final LoginRequest request = DtoCreater.create(
                LoginRequest.class,
                "newSocialId",
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
        final List<String> memberSocialIds = memberRepository
                .findAll()
                .stream()
                .map(Member::getSocialId)
                .toList();

        // then
        assertSoftly(
                softly -> {
                    assertThat(refreshTokens).contains(memberToken.getRefreshToken());
                    assertThat(memberSocialIds).contains("newSocialId");
                }
        );
    }
}
