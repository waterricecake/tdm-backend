package tdm.tdmbackend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RefreshTokenException extends CustomException{

    private final String refreshToken;
    private final int errorCode;
    private final String errorMessage;

    public static RefreshTokenException of(final String refreshToken, final ExceptionCode exceptionCode){
        return new RefreshTokenException(refreshToken,exceptionCode.getErrorCode(), exceptionCode.getErrorMessage());
    }
}
