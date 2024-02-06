package tdm.tdmbackend.login.dto.request;

import static lombok.AccessLevel.PROTECTED;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class LoginRequest {

    private String socialId;
    private String nickname;
    private String profile;
}
