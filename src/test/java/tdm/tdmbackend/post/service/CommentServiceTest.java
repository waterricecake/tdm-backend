package tdm.tdmbackend.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import tdm.tdmbackend.global.DtoCreater;
import tdm.tdmbackend.global.ServiceTest;
import tdm.tdmbackend.global.exception.BadRequestException;
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

    @ParameterizedTest(name = "{0}일때 댓글 생성에 실패한다")
    @MethodSource("createFailCaseProvider")
    void create_Fail(
            final String testCase,
            final long postId,
            final long memberId,
            final Class<Exception> errorClass
    ) {
        // given
        final CommentRequest commentRequest = DtoCreater.create(
                CommentRequest.class,
                "createContent"
        );

        assertThatThrownBy(() -> commentService.create(commentRequest, postId, memberId))
                .isInstanceOf(errorClass);
    }

    static Stream<Arguments> createFailCaseProvider() {
        return Stream.of(
                Arguments.of("해당하는 게시물이 없을 때", -1L, 1L, BadRequestException.class),
                Arguments.of("해당하는 멤버가 없을 때", 1L, -1L, BadRequestException.class)
        );
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

    @ParameterizedTest(name = "{0}일때 댓글 수정에 실패한다")
    @MethodSource("updateFailCaseProvider")
    void update_Fail(
            final String testCase,
            final long commentId,
            final Class<Exception> errorClass
    ) {
        // given
        final CommentRequest commentRequest = DtoCreater.create(
                CommentRequest.class,
                "createContent"
        );

        assertThatThrownBy(() -> commentService.update(commentRequest, commentId))
                .isInstanceOf(errorClass);
    }

    static Stream<Arguments> updateFailCaseProvider() {
        return Stream.of(
                Arguments.of("해당하는 댓글이 없을 때", -1L, BadRequestException.class)
        );
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
