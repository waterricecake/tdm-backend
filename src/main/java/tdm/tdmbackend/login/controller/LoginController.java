package tdm.tdmbackend.login.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.CREATED;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdm.tdmbackend.auth.Auth;
import tdm.tdmbackend.auth.MemberOnly;
import tdm.tdmbackend.auth.domain.Accessor;
import tdm.tdmbackend.login.domain.MemberToken;
import tdm.tdmbackend.login.dto.request.LoginRequest;
import tdm.tdmbackend.login.dto.response.AccessTokenResponse;
import tdm.tdmbackend.login.service.LoginService;

@Tag(name = "00. Authorization API", description = "인증인가")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class LoginController {

    private final LoginService loginService;

    @Operation(
            summary = "로그인"
    )
    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(
            @RequestBody final LoginRequest loginRequest,
            final HttpServletResponse response
    ) {
        final MemberToken memberToken = loginService.login(loginRequest);
        final ResponseCookie cookie = ResponseCookie.from("refresh-token", memberToken.getRefreshToken())
                .maxAge(604800)
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();
        response.addHeader(SET_COOKIE, cookie.toString());
        return ResponseEntity.status(CREATED).body(AccessTokenResponse.from(memberToken.getAccessToken()));
    }

    @Operation(
            summary = "access 토큰 재발급",
            description = "로그인 필요",
            security = {@SecurityRequirement(name = AUTHORIZATION), @SecurityRequirement(name = "refreshToken")}
    )
    @GetMapping("/token")
    public ResponseEntity<AccessTokenResponse> reissueAccessToken(
            @CookieValue("refresh-token") final String refreshToken,
            @RequestHeader("Authorization") final String authorization
    ) {
        final String accessToken = loginService.reissueAccessToken(refreshToken, authorization);
        return ResponseEntity.status(CREATED).body(AccessTokenResponse.from(accessToken));
    }

    @Operation(
            summary = "로그아웃",
            description = "로그인 필요",
            security = {@SecurityRequirement(name = AUTHORIZATION), @SecurityRequirement(name = "refreshToken")}
    )
    @DeleteMapping("/logout")
    @MemberOnly
    public ResponseEntity<Void> logout(
            @Auth Accessor accessor,
            @CookieValue("refresh-token") final String refreshToken
    ) {
        loginService.logout(refreshToken);
        return ResponseEntity.noContent().build();
    }
}
