package tdm.tdmbackend.post.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tdm.tdmbackend.member.domain.Member;

@Getter
@RequiredArgsConstructor
public class AuthorResponse {

    private final Long id;
    private final String name;
    private final String school;
    private final Long level;

    public static AuthorResponse from(final Member member) {
        return new AuthorResponse(
                member.getId(),
                member.getNickname(),
                member.getSchool(),
                member.getLevel()
        );
    }
}
