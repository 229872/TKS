package pl.lodz.p.edu.adapter.repository.clients.exception;

public class EntityNotFoundRepositoryException extends Exception {
    public EntityNotFoundRepositoryException() {
    }

    public EntityNotFoundRepositoryException(String message) {
        super(message);
    }

    public EntityNotFoundRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundRepositoryException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
