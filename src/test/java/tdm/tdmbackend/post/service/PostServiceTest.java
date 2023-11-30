package tdm.tdmbackend.post.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import tdm.tdmbackend.global.DtoCreater;
import tdm.tdmbackend.global.ServiceTest;
import tdm.tdmbackend.post.domain.Image;
import tdm.tdmbackend.post.domain.Post;
import tdm.tdmbackend.post.domain.PostTag;
import tdm.tdmbackend.post.dto.request.PostRequest;
import tdm.tdmbackend.post.dto.response.PostDetailResponse;
import tdm.tdmbackend.post.repository.ImageRepository;
import tdm.tdmbackend.post.repository.PostRepository;
import tdm.tdmbackend.post.repository.PostTagRepository;

@Import(PostService.class)
class PostServiceTest extends ServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostTagRepository postTagRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    @DisplayName("게시물을 생성한다")
    void create() {
        // given
        final PostRequest request = DtoCreater.create(
                PostRequest.class,
                "testTitle",
                "testContent",
                List.of(4L, 5L, 6L),
                List.of("update.jpeg", "update.jpg")
        );

        // when
        final Long postId = postService.create(request, 1L);
        final Post post = postRepository.findById(postId).orElseThrow();
        final List<String> images = imageRepository.findImagesByPost(post).stream()
                .map(Image::getName)
                .toList();
        final List<Long> postTags = postTagRepository.findPostTagsByPost(post).stream()
                .map(postTag -> postTag.getTag().getId())
                .toList();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(post.getTitle()).isEqualTo(request.getTitle());
                    softly.assertThat(post.getContent()).isEqualTo(request.getContent());
                    softly.assertThat(images).containsAll(request.getImages());
                    softly.assertThat(postTags).containsAll(request.getTags());
                }
        );
    }

    @Test
    @DisplayName("게시물을 조회한다")
    void read() {
        // when
        final PostDetailResponse response = postService.read(1L);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.getId()).isEqualTo(1L);
                    softly.assertThat(response.getTitle()).isEqualTo("test1");
                    softly.assertThat(response.getContent()).isEqualTo("content");
                    softly.assertThat(response.getImages()).containsExactlyInAnyOrder("test1.jpeg", "test2.jpeg");
                    softly.assertThat(response.getTags()).containsExactlyInAnyOrder(1L, 2L, 3L);
                    softly.assertThat(response.getAuthor().getId()).isEqualTo(1L);
                    softly.assertThat(response.getAuthor().getName()).isEqualTo("name");
                    softly.assertThat(response.getAuthor().getProfile()).isEqualTo("https://profile.jpg");
                    softly.assertThat(response.getAuthor().getSchool()).isEqualTo("school");
                    softly.assertThat(response.getAuthor().getLevel()).isEqualTo(1L);
                    softly.assertThat(response.getComments()).hasSize(2);
                }
        );
    }

    @Test
    @DisplayName("게시물을 수정한다")
    void update() {
        // given
        final PostRequest request = DtoCreater.create(
                PostRequest.class,
                "testTitle",
                "testContent",
                List.of(4L, 5L, 6L),
                List.of("update.jpeg", "update.jpg")
        );

        // when
        postService.update(request, 1L);
        final Post post = postRepository.findById(1L).orElseThrow();
        final List<String> images = imageRepository.findImagesByPost(post).stream()
                .map(Image::getName)
                .toList();
        final List<Long> postTags = postTagRepository.findPostTagsByPost(post).stream()
                .map(postTag -> postTag.getTag().getId())
                .toList();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(post.getTitle()).isEqualTo(request.getTitle());
                    softly.assertThat(post.getContent()).isEqualTo(request.getContent());
                    softly.assertThat(images).containsExactlyInAnyOrderElementsOf(request.getImages());
                    softly.assertThat(postTags).containsExactlyInAnyOrderElementsOf(request.getTags());
                }
        );
    }

    @Test
    @DisplayName("게시물을 삭제한다")
    void delete() {
        // when
        postService.delete(1L);
        final List<PostTag> postTags = postTagRepository.findAll().stream()
                .filter(postTag -> postTag.getPost().getId().equals(1L))
                .toList();
        final List<Image> images = imageRepository.findAll().stream()
                .filter(image -> image.getPost().getId().equals(1L))
                .toList();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThatThrownBy(() -> postRepository.findById(1L).orElseThrow());
                    softly.assertThat(postTags).hasSize(0);
                    softly.assertThat(images).hasSize(0);
                }
        );
    }
}
