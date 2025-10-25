package com.EstudosJpaSpring.livariaapi.controller;

import com.EstudosJpaSpring.livariaapi.controller.dto.AutorDTO;
import com.EstudosJpaSpring.livariaapi.controller.dto.ErroResposta;
import com.EstudosJpaSpring.livariaapi.exception.OperacaoNaoPermitidaException;
import com.EstudosJpaSpring.livariaapi.exception.RegistorDuplicadoAutor;
import com.EstudosJpaSpring.livariaapi.model.Autor;
import com.EstudosJpaSpring.livariaapi.sefvice.AutorService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AutorController {

    private AutorService service;

    public AutorController(AutorService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody AutorDTO autor){

        try {Autor autorDto = autor.mapearAutor();
        service.salvar(autorDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(autorDto.getId()).toUri();
        return ResponseEntity.created(location).build();} catch (RegistorDuplicadoAutor e){
            var erroResposta = ErroResposta.respostaConflito(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
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
    public ResponseEntity<Object> deletarAutor(@PathVariable String id){
        try{
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if (autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }service.deletarPorId(autorOptional.get());
        return ResponseEntity.notFound().build();} catch (OperacaoNaoPermitidaException o){
            var erroResposta = ErroResposta.respostaPadrao(o.getMessage());
            return  ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }

    }
    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisa(@RequestParam(value = "nome", required = false) String nome,@RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        List<Autor> resultado = service.pesquisa(nome, nacionalidade);
        List<AutorDTO> autorDto = resultado.stream().map(autor -> new AutorDTO(autor.getId(),autor.getNome(),autor.getDataNascimento(),autor.getNacionalidade())).collect(Collectors.toList());
        return ResponseEntity.ok(autorDto);
    }
    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable String id,@RequestBody AutorDTO autorDTO){

        try {
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
        return ResponseEntity.noContent().build();}catch (RegistorDuplicadoAutor e){
            var erroResposta = ErroResposta.respostaConflito(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);

        }
    }

}
