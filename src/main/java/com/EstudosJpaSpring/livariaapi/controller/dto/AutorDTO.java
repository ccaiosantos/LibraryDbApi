package com.EstudosJpaSpring.livariaapi.controller.dto;

import com.EstudosJpaSpring.livariaapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(UUID id,
                       @NotBlank(message = "campo obrigatório") @Size(max = 100, min = 2, message = "Fora do tamanho padrão") String nome,
                       @NotNull(message = "campo obrigatório") @Past(message = "não pode ser uma data futura") LocalDate dataNascimento,
                       @NotBlank(message = "campo obrigatório") @Size(max = 50, min = 2, message = "Fora do tamanho padrão") String nacionalidade) {

    public Autor mapearAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setNacionalidade(this.nacionalidade);
        autor.setDataNascimento(this.dataNascimento);
        return autor;
    }
}
