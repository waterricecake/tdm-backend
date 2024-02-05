package tdm.tdmbackend.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tdm.tdmbackend.jwt.domain.MemberToken;
import tdm.tdmbackend.jwt.domain.RefreshToken;
import tdm.tdmbackend.jwt.dto.request.LoginRequest;
import tdm.tdmbackend.jwt.repository.RefreshTokenRepository;
import tdm.tdmbackend.jwt.util.JwtProvider;
import tdm.tdmbackend.member.domain.Member;
import tdm.tdmbackend.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public MemberToken login(final LoginRequest request) {
        if (!memberRepository.existsMemberBySocialId(request.getSocialId())) {
            signUp(request);
        }
        final MemberToken memberToken = jwtProvider.createMemberToken(request.getSocialId());
        refreshTokenRepository.save(RefreshToken.from(memberToken.getRefreshToken()));
        return memberToken;
    }

    private void signUp(final LoginRequest request){
        memberRepository.save(
                Member.of(
                        request.getNickname(),
                        request.getProfile(),
                        request.getSocialId()
                )
        );
    }
}
