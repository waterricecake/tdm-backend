package tdm.tdmbackend.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tdm.tdmbackend.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
