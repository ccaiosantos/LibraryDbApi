package com.EstudosJpaSpring.livariaapi.controller.dto;

import com.EstudosJpaSpring.livariaapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(UUID id,
                       @NotBlank(message = "campo obrigatório") String nome,
                       @NotNull(message = "campo obrigatório") LocalDate dataNascimento,
                       @NotBlank(message = "campo obrigatório") String nacionalidade) {

    public Autor mapearAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setNacionalidade(this.nacionalidade);
        autor.setDataNascimento(this.dataNascimento);
        return autor;
    }
}
