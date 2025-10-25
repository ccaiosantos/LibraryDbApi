package com.EstudosJpaSpring.livariaapi.sefvice;

import com.EstudosJpaSpring.livariaapi.model.Autor;
import com.EstudosJpaSpring.livariaapi.repository.AutorRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {
    private final AutorRepository repository;

    public AutorService(AutorRepository repository){
        this.repository = repository;
    }

    public Autor salvar(Autor autor){
        return repository.save(autor);
    }

    public void atualizar(Autor autor){
        if (autor.getId() == null){
            throw new IllegalArgumentException("Não Existe Autor para atualizar"); //throw new é lançar
        }
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletarPorId(Autor autor){
        repository.delete(autor);
    }
//    public List<Autor> pesquisa(String nome, String nacionalidade){
//        if (nome !=  null && nacionalidade != null){
//            return repository.findByNomeAndNacionalidade(nome,nacionalidade);
//        }
//        if(nome != null){
//            return repository.findByNome(nome);
//        }if(nacionalidade != null){
//            return repository.findByNome(nacionalidade);
//        }
//        return repository.findAll();
//    }

    public List<Autor> pesquisaExample(String nome, String nacionalidade){
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return repository.findAll(autorExample);  //buscar todos os autores que atendam a esse autorExample que fiz a pesquisa
    }
}
