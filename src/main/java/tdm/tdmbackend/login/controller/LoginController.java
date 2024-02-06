package tdm.tdmbackend.login.controller;

import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.CREATED;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdm.tdmbackend.login.domain.MemberToken;
import tdm.tdmbackend.login.dto.request.LoginRequest;
import tdm.tdmbackend.login.dto.response.AccessTokenResponse;
import tdm.tdmbackend.login.service.LoginService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class LoginController {

    private final LoginService loginService;

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

    @GetMapping("/token")
    public ResponseEntity<AccessTokenResponse> reissueAccessToken(
            @CookieValue("refresh-token") final String refreshToken,
            @RequestHeader("Authorization") final String authorization
    ){
        final String accessToken = loginService.reissueAccessToken(refreshToken,authorization);
        return ResponseEntity.status(CREATED).body(AccessTokenResponse.from(accessToken));
    }
}
