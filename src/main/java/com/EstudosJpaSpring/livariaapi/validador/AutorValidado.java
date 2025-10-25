package com.EstudosJpaSpring.livariaapi.validador;

import com.EstudosJpaSpring.livariaapi.exception.RegistorDuplicadoAutor;
import com.EstudosJpaSpring.livariaapi.model.Autor;
import com.EstudosJpaSpring.livariaapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidado {
    private AutorRepository repository;

    public AutorValidado(AutorRepository repository) {
        this.repository = repository;
    }
    public void validar(Autor autor){
        if (existeAutorCadastrado(autor)){
            throw new RegistorDuplicadoAutor("Autor já cadastrado");
        }
    }
    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(autor.getNome(),
                autor.getDataNascimento(), autor.getNacionalidade());
        //caso não exista esta fazendo o cadastrp de um novo autor
        if (autor.getId() == null){
            return autorEncontrado.isPresent();
        }
        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
