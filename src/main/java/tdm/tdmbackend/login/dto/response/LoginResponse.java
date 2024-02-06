package tdm.tdmbackend.login.dto.response;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = PRIVATE)
@Getter
@NoArgsConstructor(access = PRIVATE)
public class LoginResponse {

    private String accessToken;

    public static LoginResponse from(final String accessToken) {
        return new LoginResponse(accessToken);
    }
}
