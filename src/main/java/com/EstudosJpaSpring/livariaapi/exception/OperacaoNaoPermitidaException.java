package com.EstudosJpaSpring.livariaapi.exception;

public class OperacaoNaoPermitidaException extends RuntimeException{
    public OperacaoNaoPermitidaException(String message) {
        super(message);
    }
}
