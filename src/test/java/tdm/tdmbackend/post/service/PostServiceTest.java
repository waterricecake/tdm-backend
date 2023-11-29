package tdm.tdmbackend.post.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tdm.tdmbackend.global.DtoCreater;
import tdm.tdmbackend.global.ServiceTest;
import tdm.tdmbackend.member.domain.Member;
import tdm.tdmbackend.member.repository.MemberRepository;
import tdm.tdmbackend.post.domain.Post;
import tdm.tdmbackend.post.dto.request.PostCreateRequest;
import tdm.tdmbackend.post.repository.PostRepository;
import tdm.tdmbackend.post.repository.PostTagRepository;

class PostServiceTest extends ServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PostTagRepository postTagRepository;

    @Test
    void create() {
        // given
        Member member = DtoCreater.create(Member.class, 1L, "nickname", "image.jpeg", "socialId", "school", 1L);
        Post post = DtoCreater.create(Post.class, 1L, member, "title", "content");
        when(memberRepository.findById(any()))
                .thenReturn(Optional.of(member));
        when(postRepository.save(any()))
                .thenReturn(post);
        when(postTagRepository.saveAll(any()))
                .thenReturn(List.of());
        PostCreateRequest request = DtoCreater.create(
                PostCreateRequest.class,
                "testTitle",
                "testContent",
                List.of(1L, 2L, 3L),
                List.of("test.jpeg", "test.jpg")
        );

        // when
        postService.create(request, member.getId());

        // then
        verify(memberRepository).findById(any());
        verify(postRepository).save(any());
        verify(postTagRepository).saveAll(any());
    }
}
