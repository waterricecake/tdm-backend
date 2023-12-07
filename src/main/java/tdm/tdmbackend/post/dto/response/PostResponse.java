package tdm.tdmbackend.post.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tdm.tdmbackend.post.domain.Post;

@Getter
@RequiredArgsConstructor
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String image;

    public static PostResponse from(Post post){
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getFirstImageUrl()
        );
    }
}
