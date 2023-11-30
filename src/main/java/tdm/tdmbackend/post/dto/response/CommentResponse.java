package tdm.tdmbackend.post.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tdm.tdmbackend.comment.domain.Comment;

@RequiredArgsConstructor
@Getter
public class CommentResponse {
    private final Long id;
    private final String content;
    private final AuthorResponse member;

    public static CommentResponse from(final Comment comment){
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                AuthorResponse.from(comment.getMember())
        );
    }

}
