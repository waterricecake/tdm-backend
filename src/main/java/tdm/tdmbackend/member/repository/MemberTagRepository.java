package tdm.tdmbackend.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tdm.tdmbackend.member.domain.MemberTag;

public interface MemberTagRepository extends JpaRepository<MemberTag, Long> {

    void deleteMemberTagsByMemberId(final Long memberId);
}
