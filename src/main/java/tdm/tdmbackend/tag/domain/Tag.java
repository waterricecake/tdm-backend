package tdm.tdmbackend.tag.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tdm.tdmbackend.global.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Tag extends BaseEntity {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
