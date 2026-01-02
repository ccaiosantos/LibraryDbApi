package com.EstudosJpaSpring.livariaapi.controller;

import com.EstudosJpaSpring.livariaapi.controller.dto.CadastroLivroDTO;
import com.EstudosJpaSpring.livariaapi.controller.dto.ErroResposta;
import com.EstudosJpaSpring.livariaapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.EstudosJpaSpring.livariaapi.controller.mappers.LivroMapper;
import com.EstudosJpaSpring.livariaapi.exception.RegistorDuplicadoAutor;
import com.EstudosJpaSpring.livariaapi.model.GeneroLivro;
import com.EstudosJpaSpring.livariaapi.model.Livro;
import com.EstudosJpaSpring.livariaapi.sefvice.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {
    private final LivroService livroService;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO cadastroLivroDTO) {


        Livro livro = mapper.toEntity(cadastroLivroDTO);
        livroService.salvarLivro(livro);
        var url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> mapear(@PathVariable("id") String id){
        return livroService.obterPorId(UUID.fromString(id)).map(livro -> {
            var dto = mapper.toDto(livro);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarLivro(@PathVariable("id") String id){
        return livroService.obterPorId(UUID.fromString(id)).map(livro -> {
            livroService.deletarPorId(livro);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }
    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nomeAutor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "anoPublicacao", required = false)
            Integer anoPublicaco
    ){
        var resultado = livroService.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicaco);
        var lista = resultado.stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id")String id, @RequestBody @Valid CadastroLivroDTO dto){
       return livroService.obterPorId(UUID.fromString(id))
               .map(livro -> {
                   Livro entity = mapper.toEntity(dto);
                   
                   livro.setTitulo(entity.getTitulo());
                   livro.setAutor(entity.getAutor());
                   livro.setGenero(entity.getGenero());
                   livro.setIsbn(entity.getIsbn());
                   livro.setPreco(entity.getPreco());
                   livro.setDataPublicacao(entity.getDataPublicacao());
                   
                   livroService.atualizar(livro);
                   return ResponseEntity.noContent().build();
               }).orElseGet(()-> ResponseEntity.notFound().build());
    }
    
}
