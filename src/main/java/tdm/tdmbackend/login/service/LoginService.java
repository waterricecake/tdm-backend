package tdm.tdmbackend.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tdm.tdmbackend.login.domain.MemberToken;
import tdm.tdmbackend.login.domain.RefreshToken;
import tdm.tdmbackend.login.dto.request.LoginRequest;
import tdm.tdmbackend.login.repository.RefreshTokenRepository;
import tdm.tdmbackend.login.util.JwtManager;
import tdm.tdmbackend.member.domain.Member;
import tdm.tdmbackend.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
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
}