package com.EstudosJpaSpring.livariaapi.controller;

import com.EstudosJpaSpring.livariaapi.controller.dto.AutorDTO;
import com.EstudosJpaSpring.livariaapi.model.Autor;
import com.EstudosJpaSpring.livariaapi.sefvice.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private AutorService service;

    public AutorController(AutorService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO autor){
        Autor autorDto = autor.mapearAutor();
        service.salvar(autorDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(autorDto.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity obterDadosAutor(@PathVariable String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional= service.obterPorId(idAutor);
        if (autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO autorDTO = new AutorDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());
            return ResponseEntity.ok(autorDTO);
        } return ResponseEntity.notFound().build();


    }

    @DeleteMapping("{id}")//void por que não vai retornar nada
    public ResponseEntity<Void> deletarAutor(@PathVariable String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if (autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }service.deletarPorId(autorOptional.get());
        return ResponseEntity.notFound().build();

    }
    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisa(@RequestParam(value = "nome", required = false) String nome,@RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        List<Autor> resultado = service.pesquisaExample(nome, nacionalidade);
        List<AutorDTO> autorDto = resultado.stream().map(autor -> new AutorDTO(autor.getId(),autor.getNome(),autor.getDataNascimento(),autor.getNacionalidade())).collect(Collectors.toList());
        return ResponseEntity.ok(autorDto);
    }
    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable String id,@RequestBody @Valid AutorDTO autorDTO){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if (autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var autor = autorOptional.get(); //entidade autor qu eveio do banco
        autor.setNome(autorDTO.nome());
        autor.setNacionalidade(autorDTO.nacionalidade());
        autor.setDataNascimento(autorDTO.dataNascimento());
        service.atualizar(autor);
        return ResponseEntity.noContent().build();
    }

}
