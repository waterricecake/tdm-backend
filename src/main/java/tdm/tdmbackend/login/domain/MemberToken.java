package tdm.tdmbackend.login.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberToken {

    private final String accessToken;
    private final String refreshToken;
}
