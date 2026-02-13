package com.salvatore.agenziaViaggi.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private List<String> errorsMessages;
    public ValidationException(List<String> errorsMessages) {

        super("ATTENZIONE! Errori nel payload");
        this.errorsMessages = errorsMessages;
    }
}
