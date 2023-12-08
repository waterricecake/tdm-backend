package tdm.tdmbackend.tag.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tdm.tdmbackend.tag.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("""
            SELECT tag
            FROM Tag tag, MemberTag membertag
            WHERE tag.id = membertag.tag.id
            AND membertag.member.id = :memberId
            """)
    List<Tag> findTagsByMemberId(@Param("memberId") final Long memberId);
}
