package com.EstudosJpaSpring.livariaapi.controller.commom;

import com.EstudosJpaSpring.livariaapi.controller.dto.ErroCampo;
import com.EstudosJpaSpring.livariaapi.controller.dto.ErroResposta;
import com.EstudosJpaSpring.livariaapi.exception.OperacaoNaoPermitidaException;
import com.EstudosJpaSpring.livariaapi.exception.RegistorDuplicadoAutor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class GlobalExceptionHnadler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors.stream().map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage())).collect(Collectors.toList());
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação",listaErros);
    }
    @ExceptionHandler(RegistorDuplicadoAutor.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handelRegistroDuplicadoException(RegistorDuplicadoAutor e){
        return ErroResposta.respostaConflito(e.getMessage());
    }
    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handlerOperacaoNaoPermitadaException(OperacaoNaoPermitidaException e){
        return ErroResposta.respostaPadrao(e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErroNaoTratado(RuntimeException e){
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Falha no servidor interno", List.of());

    }
}
