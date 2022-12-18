package com.example.demo.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(long id) {
        super("Запись не найдена");
    }
}
