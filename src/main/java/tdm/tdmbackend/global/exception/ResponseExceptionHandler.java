package tdm.tdmbackend.global.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tdm.tdmbackend.login.repository.RefreshTokenRepository;

@RestControllerAdvice
@RequiredArgsConstructor
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    // todo : event 처리
    private final RefreshTokenRepository refreshTokenRepository;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(final BadRequestException exception){
        return ResponseEntity.badRequest().body(ExceptionResponse.from(exception));
    }

    @ExceptionHandler(AccessTokenException.class)
    public ResponseEntity<ExceptionResponse> handleAuthTokenException(final AccessTokenException exception){
        return ResponseEntity.badRequest().body(ExceptionResponse.from(exception));
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ExceptionResponse> handleRefreshTokenException(final RefreshTokenException exception){
        // todo : event 처리
        refreshTokenRepository.deleteRefreshTokenByToken(exception.getRefreshToken());
        return ResponseEntity.badRequest().body(ExceptionResponse.from(exception));
    }
}
