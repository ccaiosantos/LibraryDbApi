package com.EstudosJpaSpring.livariaapi.controller.mappers;

import com.EstudosJpaSpring.livariaapi.controller.dto.CadastroLivroDTO;
import com.EstudosJpaSpring.livariaapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.EstudosJpaSpring.livariaapi.exception.RegistorDuplicadoAutor;
import com.EstudosJpaSpring.livariaapi.model.Livro;
import com.EstudosJpaSpring.livariaapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring" , uses = AutorMapper.class)
public abstract class LivroMapper {
    @Autowired
    public AutorRepository repository;

    @Mapping(target = "autor", expression = "java(repository.findById(dto.idAutor() ).orElse(null))")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDto(Livro livro);
}
