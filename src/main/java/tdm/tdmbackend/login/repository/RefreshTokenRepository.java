package tdm.tdmbackend.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tdm.tdmbackend.login.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Boolean existsRefreshTokenByRefreshToken(final String refreshToken);
}
