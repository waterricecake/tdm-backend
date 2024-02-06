package tdm.tdmbackend.login.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tdm.tdmbackend.login.domain.MemberToken;
import tdm.tdmbackend.login.domain.RefreshToken;
import tdm.tdmbackend.login.dto.request.LoginRequest;
import tdm.tdmbackend.login.repository.RefreshTokenRepository;
import tdm.tdmbackend.login.util.AccessTokenExtractor;
import tdm.tdmbackend.login.util.JwtManager;
import tdm.tdmbackend.member.domain.Member;
import tdm.tdmbackend.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class LoginService {

    private final JwtManager jwtManager;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public MemberToken login(final LoginRequest request) {
        Member member = memberRepository.findMemberBySocialId(request.getSocialId())
                .orElseGet(() -> signUp(request));
        final MemberToken memberToken = jwtManager.createMemberToken(member.getId().toString());
        refreshTokenRepository.save(RefreshToken.from(memberToken.getRefreshToken()));
        return memberToken;
    }

    private Member signUp(final LoginRequest request) {
        return memberRepository.save(
                Member.of(
                        request.getNickname(),
                        request.getProfile(),
                        request.getSocialId()
                )
        );
    }

    public String reissueAccessToken(final String refreshToken, final String authorization) {
        final String accessToken = AccessTokenExtractor.extractAccessToken(authorization);
        jwtManager.validateTokens(new MemberToken(accessToken, refreshToken));
        final String memberId = jwtManager.parseSubject(refreshToken);
        return jwtManager.createReissueAccessToken(memberId);
    }

    public void logout(final String refreshToken) {
        refreshTokenRepository.deleteRefreshTokenByToken(refreshToken);
    }
}
