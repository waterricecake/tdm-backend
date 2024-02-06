package tdm.tdmbackend.auth.domain;

import static lombok.AccessLevel.PRIVATE;
import static tdm.tdmbackend.auth.domain.Authority.GUEST;
import static tdm.tdmbackend.auth.domain.Authority.MEMBER;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = PRIVATE)
@Getter
public class Accessor {

    private final Long memberId;
    private final Authority authority;

    public static Accessor guest() {
        return new Accessor(-1L, GUEST);
    }

    public static Accessor member(final Long memberId) {
        return new Accessor(memberId, MEMBER);
    }

    public boolean isMember() {
        return this.authority.equals(MEMBER);
    }
}
