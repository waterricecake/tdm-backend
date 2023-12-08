package tdm.tdmbackend.member.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tdm.tdmbackend.member.domain.Member;
import tdm.tdmbackend.member.dto.response.MyPageResponse;
import tdm.tdmbackend.member.repository.MemberRepository;
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

    public MyPageResponse getMyPage(final Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow();
        List<Post> posts = postRepository.findPostsByMember(member);
        List<Tag> tags = tagRepository.findTagsByMemberId(memberId);
        return MyPageResponse.of(member, posts, tags);
    }
}
