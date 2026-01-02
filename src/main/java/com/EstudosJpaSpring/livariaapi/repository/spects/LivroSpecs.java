package com.EstudosJpaSpring.livariaapi.repository.spects;

import com.EstudosJpaSpring.livariaapi.model.GeneroLivro;
import com.EstudosJpaSpring.livariaapi.model.Livro;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {
    public static Specification<Livro> isbnEquals(String isbn){
        return ((root, query, cb) ->cb.equal(root.get("isbn"), isbn) );

    }
    public static Specification<Livro> tituloLike(String titulo){
        return (root, query, cb) -> cb.like(cb.upper(root.get("titulo")), "%"+titulo.toUpperCase()+"%");

    }
    public static Specification<Livro> generoEquals(GeneroLivro generoLivro){
        return (root, query, cb) -> cb.equal(root.get("genero"),generoLivro);
    }
    public static Specification<Livro> anoPublicacaoEquals(Integer anoPublicacao){
        return (root, query, cb) -> cb.equal(cb.function("to_char", String.class, cb.literal("YYYY")),anoPublicacao.toString());
    }
    public static Specification<Livro> nomeAutorLike(String nome){
        return (root, query, cb) -> {
            return cb.like(cb.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%");
        };
    }
}
