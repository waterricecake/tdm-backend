package tdm.tdmbackend.member.domain;

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

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profile;

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    private String school;

    @Column(nullable = false)
    private Long level;

    private Member(
            final Long id,
            final String nickname,
            final String profile,
            final String socialId,
            final String school,
            final Long level
    ) {
        super(USABLE);
        this.id = id;
        this.nickname = nickname;
        this.profile = profile;
        this.socialId = socialId;
        this.school = school;
        this.level = level;
    }

    public static Member from(
            final String nickname,
            final String profile,
            final String socialId,
            final String school,
            final Long level
    ) {
        return new Member(null, nickname, profile, socialId, school, level);
    }
}
