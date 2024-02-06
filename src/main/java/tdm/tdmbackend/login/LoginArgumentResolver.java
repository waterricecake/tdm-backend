package tdm.tdmbackend.login;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import tdm.tdmbackend.auth.Auth;
import tdm.tdmbackend.auth.domain.Accessor;
import tdm.tdmbackend.login.domain.MemberToken;
import tdm.tdmbackend.login.repository.RefreshTokenRepository;
import tdm.tdmbackend.login.util.AccessTokenExtractor;
import tdm.tdmbackend.login.util.JwtManager;

@RequiredArgsConstructor
@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtManager jwtManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.withContainingClass(Long.class)
                .hasParameterAnnotation(Auth.class);
    }

    @Override
    public Accessor resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        final HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            //예외처리
            throw new IllegalArgumentException();
        }

        try {
            final String accessToken = AccessTokenExtractor.extractAccessToken(webRequest.getHeader(AUTHORIZATION));
            final String refreshToken = extractRefreshToken(request.getCookies());
            final MemberToken memberToken = new MemberToken(accessToken, refreshToken);
            jwtManager.validateTokens(memberToken);

            final Long memberId = Long.parseLong(jwtManager.parseSubject(accessToken));
            return Accessor.member(memberId);
            // todo : 예외처리 -> cookie에 값이 없거나 refreshToken이 기간이 지났을 경우
        } catch (Exception e) {
            return Accessor.guest();
        }
    }

    private String extractRefreshToken(final Cookie... cookies) {
        if (cookies == null) {
            // todo : 예외처리
            throw new IllegalArgumentException();
        }
        return Arrays.stream(cookies)
                .filter(this::isValidRefreshToken)
                .findFirst()
                // todo : 예외처리
                .orElseThrow(IllegalArgumentException::new)
                .getValue();
    }

    private boolean isValidRefreshToken(final Cookie cookie) {
        return cookie.getName().equals("refresh-token")
                && refreshTokenRepository.existsRefreshTokenByRefreshToken(cookie.getValue());
    }
}
