package tdm.tdmbackend.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tdm.tdmbackend.member.domain.Member;
import tdm.tdmbackend.member.repository.MemberRepository;
import tdm.tdmbackend.post.domain.Post;
import tdm.tdmbackend.post.domain.PostTag;
import tdm.tdmbackend.post.dto.request.PostCreateRequest;
import tdm.tdmbackend.post.repository.PostRepository;
import tdm.tdmbackend.post.repository.PostTagRepository;
import tdm.tdmbackend.tag.domain.Tag;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostTagRepository postTagRepository;

    public Long create(PostCreateRequest request, Long memberId) {
        // todo: event 처리
        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalAccessError::new);
        Post savePost = Post.of(member,request.getTitle(),request.getContent());
        Post post = postRepository.save(savePost);
        postTagRepository.saveAll(
                request.getTags().stream()
                        .map(tagId -> PostTag.of(Post.from(post.getId()), Tag.from(tagId)))
                        .toList()
        );
        return post.getId();
    }
}
