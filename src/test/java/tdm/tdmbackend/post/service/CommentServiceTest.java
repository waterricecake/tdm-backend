package tdm.tdmbackend.post.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import tdm.tdmbackend.global.DtoCreater;
import tdm.tdmbackend.global.ServiceTest;
import tdm.tdmbackend.post.domain.Comment;
import tdm.tdmbackend.post.dto.request.CommentRequest;
import tdm.tdmbackend.post.repository.CommentRepository;

@Import(CommentService.class)
public class CommentServiceTest extends ServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("댓글을 생성하고 저장한다")
    void create() {
        // given
        final CommentRequest commentRequest = DtoCreater.create(
                CommentRequest.class,
                "createContent"
        );

        // when
        final Long commentId = commentService.create(commentRequest, 1L, 1L).getId();
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow();

        // then
        assertThat(comment.getContent()).isEqualTo(commentRequest.getContent());
    }

    @Test
    @DisplayName("댓글을 수정한다")
    void update() {
        // given
        final CommentRequest commentRequest = DtoCreater.create(
                CommentRequest.class,
                "updateContent"
        );

        // when
        commentService.update(commentRequest, 1L);
        final Comment comment = commentRepository.findById(1L)
                .orElseThrow();

        // then
        assertThat(comment.getContent()).isEqualTo(commentRequest.getContent());
    }

    @Test
    @DisplayName("댓글을 삭제한다")
    void delete() {
        // given
        final Long commentId = 1L;

        // when
        commentService.delete(commentId);

        // then
        assertThat(commentRepository.existsById(commentId)).isFalse();
    }
}
