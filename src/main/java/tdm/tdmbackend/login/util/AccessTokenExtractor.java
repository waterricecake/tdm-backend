package tdm.tdmbackend.login.util;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public abstract class AccessTokenExtractor {

    private static final String BEARER = "Bearer ";

    public static String extractAccessToken(final String header) {
        if (header != null && header.startsWith(BEARER)) {
            return header.substring(BEARER.length()).trim();
        }
        // todo : 예외처리
        throw new IllegalArgumentException();
    }
}
