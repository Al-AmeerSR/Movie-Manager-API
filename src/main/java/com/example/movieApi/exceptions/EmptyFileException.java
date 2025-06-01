package com.example.movieApi.exceptions;

public class EmptyFileException extends RuntimeException{

    public  EmptyFileException(String message){

        super(message);
    }
}
