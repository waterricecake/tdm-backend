package tdm.tdmbackend.tag.domain;

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
public class Tag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private Tag(final Long id, final String name) {
        super(USABLE);
        this.id = id;
        this.name = name;
    }

    public static Tag from(final Long tagId) {
        return new Tag(tagId, null);
    }
}
