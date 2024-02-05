package tdm.tdmbackend.jwt.controller;

import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.CREATED;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdm.tdmbackend.jwt.domain.MemberToken;
import tdm.tdmbackend.jwt.dto.request.LoginRequest;
import tdm.tdmbackend.jwt.dto.response.LoginResponse;
import tdm.tdmbackend.jwt.service.LoginService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(
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
        return ResponseEntity.status(CREATED).body(LoginResponse.from(memberToken.getAccessToken()));
    }
}
