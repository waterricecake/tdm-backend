package tdm.tdmbackend.login.util;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public abstract class AccessTokenExtractor {
    public static String extractAccessToken(final String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring("Bearer ".length()).trim();
        }
        // todo : 예외처리
        throw new IllegalArgumentException();
    }
}
