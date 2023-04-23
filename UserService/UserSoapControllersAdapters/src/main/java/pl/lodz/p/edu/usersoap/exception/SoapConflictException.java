package pl.lodz.p.edu.usersoap.exception;

public class SoapConflictException extends Exception {
    public SoapConflictException() {
    }

    public SoapConflictException(String message) {
        super(message);
    }

    public SoapConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public SoapConflictException(Throwable cause) {
        super(cause);
    }

    public SoapConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
