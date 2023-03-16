package pl.lodz.p.edu.core.domain.exception;

public class ObjectNotFoundServiceException extends Exception{
    public ObjectNotFoundServiceException() {
    }

    public ObjectNotFoundServiceException(String message) {
        super(message);
    }

    public ObjectNotFoundServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundServiceException(Throwable cause) {
        super(cause);
    }

    public ObjectNotFoundServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
