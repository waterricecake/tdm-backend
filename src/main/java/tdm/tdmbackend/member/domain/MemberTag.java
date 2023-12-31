package tdm.tdmbackend.member.domain;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static tdm.tdmbackend.global.type.StatusType.USABLE;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tdm.tdmbackend.global.BaseEntity;
import tdm.tdmbackend.tag.domain.Tag;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public MemberTag(final Long id, final Member member, final Tag tag) {
        super(USABLE);
        this.id = id;
        this.member = member;
        this.tag = tag;
    }

    public static MemberTag of(final Long memberId, final Long tagId) {
        return new MemberTag(
                null,
                Member.from(memberId),
                Tag.from(tagId)
        );
    }
}
