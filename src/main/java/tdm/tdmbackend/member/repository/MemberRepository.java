package tdm.tdmbackend.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tdm.tdmbackend.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberBySocialId(final String socialId);
}
