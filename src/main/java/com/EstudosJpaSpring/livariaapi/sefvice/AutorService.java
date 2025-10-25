package com.EstudosJpaSpring.livariaapi.sefvice;

import com.EstudosJpaSpring.livariaapi.exception.OperacaoNaoPermitidaException;
import com.EstudosJpaSpring.livariaapi.model.Autor;
import com.EstudosJpaSpring.livariaapi.repository.AutorRepository;
import com.EstudosJpaSpring.livariaapi.repository.LivroRepository;
import com.EstudosJpaSpring.livariaapi.validador.AutorValidado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {
    private final AutorRepository repository;
    private final AutorValidado validado;
    private final LivroRepository livroRepository;


    public Autor salvar(Autor autor){
        validado.validar(autor);
        return repository.save(autor);
    }

    public void atualizar(Autor autor){
        if (autor.getId() == null){
            throw new IllegalArgumentException("Não Existe Autor para atualizar"); //throw new é lançar
        }
        validado.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletarPorId(Autor autor){
        if (possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Autor possui livro e nãopode ser excluido");
        }
        repository.delete(autor);
    }
    public List<Autor> pesquisa(String nome, String nacionalidade){
        if (nome !=  null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome,nacionalidade);
        }
        if(nome != null){
            return repository.findByNome(nome);
        }if(nacionalidade != null){
            return repository.findByNome(nacionalidade);
        }
        return repository.findAll();
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);

    }
}
