package tdm.tdmbackend.global.exception;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = PRIVATE)
@Getter
public class ExceptionResponse {

    private final int code;
    private final String errorMessage;

    public static ExceptionResponse fromBadRequest(final BadRequestException exception){
        return new ExceptionResponse(exception.getCode(), exception.getErrorMessage());
    }
}
