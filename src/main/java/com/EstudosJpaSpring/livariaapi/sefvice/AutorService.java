package com.EstudosJpaSpring.livariaapi.sefvice;

import com.EstudosJpaSpring.livariaapi.model.Autor;
import com.EstudosJpaSpring.livariaapi.repository.AutorRepository;
import com.EstudosJpaSpring.livariaapi.validador.AutorValidado;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {
    private final AutorRepository repository;
    private final AutorValidado validado;

    public AutorService(AutorRepository repository, AutorValidado validado){
        this.repository = repository;
        this.validado = validado;
    }

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
}
