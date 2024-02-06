package tdm.tdmbackend.member.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdm.tdmbackend.member.domain.Member;
import tdm.tdmbackend.member.domain.MemberTag;
import tdm.tdmbackend.member.dto.request.InterestRequest;
import tdm.tdmbackend.member.dto.request.SchoolRequest;
import tdm.tdmbackend.member.dto.response.MyPageResponse;
import tdm.tdmbackend.member.repository.MemberRepository;
import tdm.tdmbackend.member.repository.MemberTagRepository;
import tdm.tdmbackend.post.domain.Post;
import tdm.tdmbackend.post.repository.PostRepository;
import tdm.tdmbackend.tag.domain.Tag;
import tdm.tdmbackend.tag.repository.TagRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final MemberTagRepository memberTagRepository;

    @Transactional(readOnly = true)
    public MyPageResponse getMyPage(final Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow();
        final List<Post> posts = postRepository.findPostsByMember(member);
        final List<Tag> tags = tagRepository.findTagsByMemberId(memberId);
        return MyPageResponse.of(member, posts, tags);
    }

    public void updateSchoolInfo(final Long memberId, final SchoolRequest schoolRequest) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow();
        member.updateSchool(schoolRequest.getSchool());
        member.updateGrade(schoolRequest.getGrade());
    }

    public void updateInterests(final Long memberId, final InterestRequest interestRequest) {
        memberTagRepository.deleteMemberTagsByMemberId(memberId);
        final List<MemberTag> memberTags = interestRequest.getInterests().stream()
                .map(interest -> MemberTag.of(memberId, interest))
                .toList();
        memberTagRepository.saveAll(memberTags);
    }
}
