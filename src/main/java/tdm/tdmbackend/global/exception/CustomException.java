package tdm.tdmbackend.global.exception;

public abstract class CustomException extends RuntimeException{

    abstract int getErrorCode();
    abstract String getErrorMessage();
}
