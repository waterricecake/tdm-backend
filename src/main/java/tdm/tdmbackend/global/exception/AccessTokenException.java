package tdm.tdmbackend.global.exception;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = PRIVATE)
@Getter
public class AccessTokenException extends CustomException{
    private final int errorCode;
    private final String errorMessage;

    public static AccessTokenException of(final ExceptionCode exceptionCode){
        return new AccessTokenException(exceptionCode.getErrorCode(), exceptionCode.getErrorMessage());
    }
}
