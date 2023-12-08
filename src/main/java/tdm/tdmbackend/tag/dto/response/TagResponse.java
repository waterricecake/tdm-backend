package tdm.tdmbackend.tag.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tdm.tdmbackend.tag.domain.Tag;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TagResponse {

    private final Long id;
    private final String name;

    public static TagResponse from(final Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());
    }
}
