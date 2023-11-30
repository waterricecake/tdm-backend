package tdm.tdmbackend.post.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tdm.tdmbackend.post.domain.Comment;
import tdm.tdmbackend.post.domain.Image;
import tdm.tdmbackend.post.domain.Post;
import tdm.tdmbackend.post.domain.PostTag;

@Getter
@RequiredArgsConstructor
public class PostDetailResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final List<Long> tags;
    private final List<String> images;
    private final AuthorResponse author;
    private final List<CommentResponse> comments;

    public static PostDetailResponse of(
            final Post post,
            final List<PostTag> postTags,
            final List<Image> images,
            final List<Comment> comments
    ) {
        final List<CommentResponse> commentResponses = comments.stream()
                .map(CommentResponse::from)
                .toList();
        final List<Long> tagIds = postTags.stream()
                .map(postTag -> postTag.getTag().getId())
                .toList();
        final List<String> imageUrls = images.stream()
                .map(Image::getName)
                .toList();
        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                tagIds,
                imageUrls,
                AuthorResponse.from(post.getMember()),
                commentResponses
        );
    }
}
