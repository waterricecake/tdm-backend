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
    private Long grade;

    private Member(
            final Long id,
            final String nickname,
            final String profile,
            final String socialId,
            final String school,
            final Long grade
    ) {
        super(USABLE);
        this.id = id;
        this.nickname = nickname;
        this.profile = profile;
        this.socialId = socialId;
        this.school = school;
        this.grade = grade;
    }

    public static Member from(
            final String nickname,
            final String profile,
            final String socialId,
            final String school,
            final Long grade
    ) {
        return new Member(null, nickname, profile, socialId, school, grade);
    }

    public void updateSchool(final String school) {
        this.school = school;
    }

    public void updateGrade(final Long grade) {
        this.grade = grade;
    }
}
