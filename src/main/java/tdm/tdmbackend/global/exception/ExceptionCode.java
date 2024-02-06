package tdm.tdmbackend.global.exception;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = PRIVATE)
@Getter
public enum ExceptionCode {
    BAD_REQUEST(1000,"잘못된 요청입니다."),
    // 2000 ~ 2999 (멤버관련)
    NO_SUCH_MEMBER(2001,"해당하는 사용자가 존재하지 않습니다."),
    NOT_FOR_GUEST(2002,"로그인이 필요합니다."),

    // 3000 ~ 3999 (게시물 관련)
    NO_SUCH_POST(3001, "해당하는 게시물이 존재하지 않습니다."),

    // 4000 ~4999 (댓글 관련)
    NO_SUCH_COMMENT(4001,"해당하는 댓글이 존재하지 않습니다."),

    // 8000 ~8999 (로그인 관련)
    NOT_VALID_TOKEN(8001, "유효하지 않는 토큰입니다."),
    ACCESS_TOKEN_EXPIRED(8002, "access 토큰이 만료되었습니다."),
    REFRESH_TOKEN_EXPRIED(8003, "refresh 토큰이 만료되었습니다.")
    ;

    private final int errorCode;
    private final String errorMessage;
}
