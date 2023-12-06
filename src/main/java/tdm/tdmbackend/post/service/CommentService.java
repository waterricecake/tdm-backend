package tdm.tdmbackend.post.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
        // todo : 예외처리
        final Member member = memberRepository.findById(memberId)
                .orElseThrow();
        final Post post = postRepository.findById(postId)
                .orElseThrow();
        final Comment comment = commentRepository.save(Comment.of(member, post, request.getContent()));
        return CommentResponse.from(comment);
    }

    public CommentResponse update(
            final CommentRequest request,
            final Long commentId
    ) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow();
        comment.updateContent(request.getContent());
        return CommentResponse.from(comment);
    }

    public void delete(final Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
