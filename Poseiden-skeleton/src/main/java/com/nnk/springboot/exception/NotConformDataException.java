package com.nnk.springboot.exception;

/**
 * NotConformDataException Class
 *
 * @author Etternell
 *
 */

public class NotConformDataException extends RuntimeException{
    /**
     * NotConformDataException Method
     *
     * @param message the error message;
     */
    public NotConformDataException(final String message){

        super(message);
    }
}