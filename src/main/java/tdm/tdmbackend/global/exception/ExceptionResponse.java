package tdm.tdmbackend.global.exception;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = PRIVATE)
@Getter
public class ExceptionResponse {

    private final int errorCode;
    private final String errorMessage;

    public static ExceptionResponse from(final CustomException exception){
        return new ExceptionResponse(exception.getErrorCode(),exception.getErrorMessage());
    }
}
