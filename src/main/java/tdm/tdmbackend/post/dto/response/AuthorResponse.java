package tdm.tdmbackend.post.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tdm.tdmbackend.member.domain.Member;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorResponse {

    private final Long id;
    private final String name;
    private final String profile;
    private final String school;
    private final Long grade;

    public static AuthorResponse from(final Member member) {
        return new AuthorResponse(
                member.getId(),
                member.getNickname(),
                member.getProfile(),
                member.getSchool(),
                member.getGrade()
        );
    }
}
