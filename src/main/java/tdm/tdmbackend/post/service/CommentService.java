package tdm.tdmbackend.post.service;

import static tdm.tdmbackend.global.exception.ExceptionCode.NO_SUCH_COMMENT;
import static tdm.tdmbackend.global.exception.ExceptionCode.NO_SUCH_MEMBER;
import static tdm.tdmbackend.global.exception.ExceptionCode.NO_SUCH_POST;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdm.tdmbackend.global.exception.BadRequestException;
import tdm.tdmbackend.member.domain.Member;
import tdm.tdmbackend.member.repository.MemberRepository;
import tdm.tdmbackend.post.domain.Comment;
import tdm.tdmbackend.post.domain.Post;
import tdm.tdmbackend.post.dto.request.CommentRequest;
import tdm.tdmbackend.post.dto.response.CommentResponse;
import tdm.tdmbackend.post.repository.CommentRepository;
import tdm.tdmbackend.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public CommentResponse create(final CommentRequest request, final Long postId, final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BadRequestException.from(NO_SUCH_MEMBER));
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> BadRequestException.from(NO_SUCH_POST));
        final Comment comment = commentRepository.save(Comment.of(member, post, request.getContent()));
        return CommentResponse.from(comment);
    }

    public CommentResponse update(
            final CommentRequest request,
            final Long commentId
    ) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> BadRequestException.from(NO_SUCH_COMMENT));
        comment.updateContent(request.getContent());
        return CommentResponse.from(comment);
    }

    public void delete(final Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
