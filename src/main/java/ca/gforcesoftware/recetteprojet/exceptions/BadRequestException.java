package ca.gforcesoftware.recetteprojet.exceptions;

/**
 * @author gavinhashemi on 2024-10-21
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
    public BadRequestException() {
        super();
    }
}
