package pl.lodz.p.edu.usersoap.exception;

public class ObjectNotFoundSoapException extends Exception {
    public ObjectNotFoundSoapException() {
    }

    public ObjectNotFoundSoapException(String message) {
        super(message);
    }

    public ObjectNotFoundSoapException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundSoapException(Throwable cause) {
        super(cause);
    }

    public ObjectNotFoundSoapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
