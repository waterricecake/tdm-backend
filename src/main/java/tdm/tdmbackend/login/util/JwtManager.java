package tdm.tdmbackend.login.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tdm.tdmbackend.login.domain.MemberToken;

@Component
public class JwtManager {

    private final SecretKey secretKey;
    private final Long accessExpirationTime;
    private final Long refreshExpirationTime;

    public JwtManager(
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

    public void validateTokens(final MemberToken memberToken){
        if (validateIsExpired(memberToken.getAccessToken())){
            // todo : 예외처리 및 AccessToken 재발급
            throw new IllegalArgumentException();
        }
        if (validateIsExpired(memberToken.getRefreshToken())){
            // todo : 예외처리 (로그아웃)
            throw new IllegalArgumentException();
        }
    }

    private boolean validateIsExpired(final String token){
        try{
            getJwtParser().parseClaimsJws(token);
            return false;
        } catch (ExpiredJwtException e){
            return true;
        } catch (JwtException | IllegalArgumentException e){
            // todo : 예외처리 (비정상적 로그인)
            throw new IllegalArgumentException();
        }
    }

    public String parseSubject(final String token){
        return getJwtParser()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private JwtParser getJwtParser(){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
    }
}
