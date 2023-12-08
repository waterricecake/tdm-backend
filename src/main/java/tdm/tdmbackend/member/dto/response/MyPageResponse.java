package tdm.tdmbackend.member.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tdm.tdmbackend.member.domain.Member;
import tdm.tdmbackend.post.domain.Post;
import tdm.tdmbackend.post.dto.response.PostResponse;
import tdm.tdmbackend.tag.domain.Tag;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MyPageResponse {

    private final Long id;
    private final String profileUrl;
    private final String nickname;
    private final String school;
    private final Long level;
    private final List<String> interests;
    private final List<PostResponse> posts;

    public static MyPageResponse of(
            final Member member,
            final List<Post> posts,
            final List<Tag> interests
    ) {
        final List<String> tagNames = interests.stream()
                .map(Tag::getName)
                .toList();
        final List<PostResponse> postResponses = posts.stream()
                .map(PostResponse::from)
                .toList();
        return new MyPageResponse(
                member.getId(),
                member.getProfile(),
                member.getNickname(),
                member.getSchool(),
                member.getLevel(),
                tagNames,
                postResponses
        );
    }
}
