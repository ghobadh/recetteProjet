package ca.gforcesoftware.recetteprojet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gavinhashemi on 2024-10-21
 */

/*
I used this excpetion in RecetteControllerTest and RecetteServiceImplTest class file.
Of course in RecetteServiceImpl I had to explicitly use my own new exception class  , rather than using runtime exception.

When I added the ResponseStatus, when I have an error in the showing a recipe in the application, instead of showing error 500, it will show 404 not found error
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
