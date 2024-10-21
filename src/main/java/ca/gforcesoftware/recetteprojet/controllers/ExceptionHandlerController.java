package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author gavinhashemi on 2024-10-21
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {
    /*
    I moved this controller from RecetteController to here, so it is explicitly used when the error happens
    Note that, I used @ControllerAdvice instead of @Controller.
    Also, since I moved it to here, I need to set up my MockMvc in my test cases . Otherwise, the existing test cases
    will fail.
    Because we put outside the controller class in the test classes I need to add
    .setControllerAdvice(new ExceptionHandlerController()) in @Before setup() methoed when I create the MockMvc

    Since the error handling is outside of RecetteController,  I used it in image controller now. Of course,
    I have to add try/catch in the checking id
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ModelAndView handleBadRequest(Exception ex){
        log.error("-----> handleBadRequest method called." + ex.getMessage());
        log.error(ex.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("400error");
        /*
        now I can add  <p th:text="${exception.getMessage()}"></p>
        so I can pass id to the error message
         */
        modelAndView.addObject("exception", ex);
        return modelAndView;
    }
}
