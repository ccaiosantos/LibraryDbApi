package com.EstudosJpaSpring.livariaapi.sefvice;

import com.EstudosJpaSpring.livariaapi.model.Livro;
import com.EstudosJpaSpring.livariaapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;

    public Livro salvarLivro(Livro livro){
        return livroRepository.save(livro);
    }
}
