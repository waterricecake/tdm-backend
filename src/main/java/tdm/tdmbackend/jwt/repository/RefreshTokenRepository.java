package tdm.tdmbackend.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tdm.tdmbackend.jwt.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
