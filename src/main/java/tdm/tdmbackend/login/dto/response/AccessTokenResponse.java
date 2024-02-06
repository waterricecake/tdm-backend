package tdm.tdmbackend.login.dto.response;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = PRIVATE)
@Getter
@NoArgsConstructor(access = PRIVATE)
public class AccessTokenResponse {

    private String accessToken;

    public static AccessTokenResponse from(final String accessToken) {
        return new AccessTokenResponse(accessToken);
    }
}
