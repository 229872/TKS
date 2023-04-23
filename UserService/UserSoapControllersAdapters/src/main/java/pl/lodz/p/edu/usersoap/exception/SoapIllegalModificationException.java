package pl.lodz.p.edu.usersoap.exception;

public class SoapIllegalModificationException extends Exception {
    public SoapIllegalModificationException() {
    }

    public SoapIllegalModificationException(String message) {
        super(message);
    }

    public SoapIllegalModificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SoapIllegalModificationException(Throwable cause) {
        super(cause);
    }

    public SoapIllegalModificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
