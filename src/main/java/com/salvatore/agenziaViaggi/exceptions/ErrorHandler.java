package com.salvatore.agenziaViaggi.exceptions;

import com.salvatore.agenziaViaggi.exceptions.BadRequestException;
import com.salvatore.agenziaViaggi.payloads.ErrorsWhitListDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsWhitListDTO handleBadRequest(BadRequestException ex){

        return new ErrorsWhitListDTO(ex.getMessage(), LocalDateTime.now(), null);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsWhitListDTO handleNotFound(NotFoundException ex){
        return new ErrorsWhitListDTO(ex.getMessage(), LocalDateTime.now(), null);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsWhitListDTO handleValidation(MethodArgumentNotValidException ex) {
        List<String> errorsList = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        return new ErrorsWhitListDTO("Errori durante la validazione", LocalDateTime.now(), errorsList);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsWhitListDTO handleGenericServerError(Exception ex){
        ex.printStackTrace();
        return new ErrorsWhitListDTO("Errore interno del server", LocalDateTime.now(), null);
    }
}
