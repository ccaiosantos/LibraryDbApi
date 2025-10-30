package com.EstudosJpaSpring.livariaapi.controller.mappers;

import com.EstudosJpaSpring.livariaapi.controller.dto.AutorDTO;
import com.EstudosJpaSpring.livariaapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {
    AutorDTO toDto(Autor autor); //transforma dto em entidade
    Autor toEntity(AutorDTO dto); //transforma entidade em dto
}
