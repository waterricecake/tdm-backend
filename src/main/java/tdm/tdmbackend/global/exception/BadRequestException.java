package tdm.tdmbackend.global.exception;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = PRIVATE)
@Getter
public class BadRequestException extends RuntimeException{

    private final int code;
    private final String errorMessage;

    public static BadRequestException from(final ExceptionCode exceptionCode){
        return new BadRequestException(exceptionCode.getErrorCode(),exceptionCode.getErrorMessage());
    }
}
