package com.EstudosJpaSpring.livariaapi.controller;

import com.EstudosJpaSpring.livariaapi.controller.dto.CadastroLivroDTO;
import com.EstudosJpaSpring.livariaapi.controller.dto.ErroResposta;
import com.EstudosJpaSpring.livariaapi.exception.RegistorDuplicadoAutor;
import com.EstudosJpaSpring.livariaapi.model.Livro;
import com.EstudosJpaSpring.livariaapi.sefvice.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {
    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO cadastroLivroDTO){
        try {
            return ResponseEntity.ok(cadastroLivroDTO);


        }catch (RegistorDuplicadoAutor e){
            var erroDTO = ErroResposta.respostaConflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }


}
