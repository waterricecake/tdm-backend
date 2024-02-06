package tdm.tdmbackend.login.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static tdm.tdmbackend.global.type.StatusType.USABLE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tdm.tdmbackend.global.BaseEntity;

@NoArgsConstructor(access = PROTECTED)
@Entity
@Getter
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    private RefreshToken(
            final Long id,
            final String token
    ) {
        super(USABLE);
        this.id = id;
        this.token = token;
    }

    public static RefreshToken from(final String refreshToken) {
        return new RefreshToken(null, refreshToken);
    }
}
