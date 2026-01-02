package com.EstudosJpaSpring.livariaapi.sefvice;

import com.EstudosJpaSpring.livariaapi.model.GeneroLivro;
import com.EstudosJpaSpring.livariaapi.model.Livro;
import com.EstudosJpaSpring.livariaapi.repository.LivroRepository;
import com.EstudosJpaSpring.livariaapi.repository.spects.LivroSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static com.EstudosJpaSpring.livariaapi.repository.spects.LivroSpecs.*;
import static org.springframework.data.jpa.domain.Specification.where;


@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;

    public Livro salvarLivro(Livro livro){
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return livroRepository.findById(id);
    }
    public void deletarPorId(Livro livro){
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao) {

        Specification<Livro> specs = (root, query, cb) -> cb.conjunction(); // SEMPRE TRUE

        if (isbn != null && !isbn.isBlank()) {
            specs = specs.and(isbnEquals(isbn));
        }

        if (titulo != null && !titulo.isBlank()) {
            specs = specs.and(tituloLike(titulo));
        }

        if (nomeAutor != null && !nomeAutor.isBlank()) {
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        if (genero != null) {
            specs = specs.and(generoEquals(genero));
        }

        if (anoPublicacao != null) {
            specs = specs.and(anoPublicacaoEquals(anoPublicacao));
        }

        return livroRepository.findAll(specs);
    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null){
            throw new IllegalArgumentException("Tem que estar na base de dados para poder atualizar");
        }
        livroRepository.save(livro);
    }
}
