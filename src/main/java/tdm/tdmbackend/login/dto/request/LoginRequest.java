package tdm.tdmbackend.login.dto.request;

import static lombok.AccessLevel.PROTECTED;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "Login API parameter", description = "로그인/회원가입 요청 API 파라미터")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class LoginRequest {

    @Schema(name = "socialId", description = "oauth2 소셜 id", example = "socialId")
    private String socialId;

    @Schema(name = "nickname", description = "별명", example = "nickname")
    private String nickname;

    @Schema(name = "profile", description = "소셜 프로파일 이미지, url", example = "profile.com")
    private String profile;
}
