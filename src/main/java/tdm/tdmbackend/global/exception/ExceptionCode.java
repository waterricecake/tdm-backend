package tdm.tdmbackend.global.exception;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = PRIVATE)
@Getter
public enum ExceptionCode {
    COMMON(1000,""),
    // 2000~ 2999 (멤버관련)
    NO_SUCH_MEMBER(2001,"해당하는 사용자가 존재하지 않습니다."),

    // 3000~ 3999 (게시물 관련)
    NO_SUCH_POST(3001, "해당하는 게시물이 존재하지 않습니다.")
    ;

    private final int errorCode;
    private final String errorMessage;
}
