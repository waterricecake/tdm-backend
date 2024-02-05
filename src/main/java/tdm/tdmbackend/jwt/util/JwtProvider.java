package tdm.tdmbackend.jwt.util;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tdm.tdmbackend.jwt.domain.MemberToken;

@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final Long accessExpirationTime;
    private final Long refreshExpirationTime;

    public JwtProvider(
            @Value("${security.jwt.secret-key}") final String secretKey,
            @Value("${security.jwt.access-expiration-time}") final Long accessExpirationTime,
            @Value("${security.jwt.refresh-expiration-time}") final Long refreshExpirationTime
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

    public MemberToken createMemberToken(final String subject){
        String accessToken = createToken(subject,accessExpirationTime);
        String refreshToken = createToken("",refreshExpirationTime);

        return new MemberToken(accessToken,refreshToken);
    }

    private String createToken(final String subject, final Long expiration){
        Date now = new Date();
        Date expriationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expriationDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
