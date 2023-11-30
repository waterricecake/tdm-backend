package tdm.tdmbackend.post.domain;

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
public class PostTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    private PostTag(final Long id, final Post post, final Tag tag) {
        super(USABLE);
        this.id = id;
        this.post = post;
        this.tag = tag;
    }

    public static PostTag of(final Post post, final Tag tag) {
        return new PostTag(null, post, tag);
    }
}
