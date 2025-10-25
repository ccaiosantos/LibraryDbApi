package com.EstudosJpaSpring.livariaapi.exception;

public class RegistorDuplicadoAutor extends RuntimeException{
    public RegistorDuplicadoAutor(String message) {
        super(message);
    }
}
