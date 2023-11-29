package tdm.tdmbackend.post.domain;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static tdm.tdmbackend.global.type.StatusType.USABLE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tdm.tdmbackend.global.BaseEntity;
import tdm.tdmbackend.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Builder
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = REMOVE)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    private Post(
            final Long id,
            final Member member,
            final String title,
            final String content) {
        super(USABLE);
        this.id = id;
        this.member = member;
        this.title = title;
        this.content = content;
    }

    public static Post of(Member member, String title, String content) {
        return new Post(null, member, title, content);
    }

    public static Post from(final Long postId) {
        return new Post(postId, null, null, null);
    }
}
