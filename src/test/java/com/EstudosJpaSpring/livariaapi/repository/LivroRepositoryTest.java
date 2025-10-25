package com.EstudosJpaSpring.livariaapi.repository;

import com.EstudosJpaSpring.livariaapi.model.Autor;
import com.EstudosJpaSpring.livariaapi.model.GeneroLivro;
import com.EstudosJpaSpring.livariaapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {
    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvartest(){
        Livro livro = new Livro();
        livro.setTitulo("Percy Jackson e os Olimpianos");
        livro.setIsbn("198468148");
        livro.setPreco(BigDecimal.valueOf(59));
        livro.setGenero(GeneroLivro.AVENTURA);
        livro.setDataPublicacao(LocalDate.of(2005,8,10));
       Autor autor = autorRepository
                .findById(UUID.fromString("d61b9870-e17f-4fa2-9430-ed7b5a560e21")).orElse(null);
        livro.setAutor(autor);

        repository.save(livro);





    }
    @Test
    void salvarcascade(){
        Livro livro = new Livro();
        livro.setTitulo("Superman All Star");
        livro.setIsbn("40848050");
        livro.setPreco(BigDecimal.valueOf(127));
        livro.setGenero(GeneroLivro.HEROIS);
        livro.setDataPublicacao(LocalDate.of(2011,2,22));
        livro.setAutor(new Autor());

        Autor autor2 = new Autor();
        autor2.setNome("Grant Morrison");
        autor2.setNacionalidade("Escocês");
        autor2.setDataNascimento(LocalDate.of(1960,1,31));

        livro.setAutor(autor2);

        repository.save(livro);


    }

    @Test
    void salvarlivroeautor(){
        Livro livro = new Livro();
        livro.setTitulo("Jogos Vorazes");
        livro.setIsbn("70709530");
        livro.setPreco(BigDecimal.valueOf(39));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setDataPublicacao(LocalDate.of(2008,9,14));
        livro.setAutor(new Autor());

        Autor autor3 = new Autor();
        autor3.setNome("Suzanne Collins");
        autor3.setNacionalidade("Norte-Americana");
        autor3.setDataNascimento(LocalDate.of(1962,8,10));
        autorRepository.save(autor3);

        livro.setAutor(autor3);

        repository.save(livro);


    }

    @Test
    void atualizarautorlivro(){
        var id = UUID.fromString("222a39f8-bb99-4149-8f3d-37f328ba4380");
        var livroautalizar = repository.findById(id).orElse(null);
        UUID id_autor = UUID.fromString("23d5e1f2-ba9f-4458-b93b-f1f3e2e7237b");
        Autor autor = autorRepository.findById(id_autor).orElse(null);
        //aqui tu bota o que tu queres mudar
        repository.save(livroautalizar);

    }

    @Test
    void deletar(){
        var id = UUID.fromString("222a39f8-bb99-4149-8f3d-37f328ba4380");
        repository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarlivro(){
        UUID uuid = UUID.fromString("7c59e40a-81be-4cb4-b849-466fac816726");
        Livro livro = repository.findById(uuid).orElse(null);
        System.out.println("Informações sobre livro: "+livro.getTitulo());
        System.out.println("Informações sobre autor: "+livro.getAutor().getNome());

    }
    @Test
    void pessquisatitulo(){
        List<Livro> livros = repository.findByTitulo("Percy Jackson e os Olimpianos");
        livros.forEach(System.out::println);

    }

    @Test
    void pesquisatituloandpreco(){
        List<Livro> livros = repository.findByTituloAndPreco("Percy Jackson e os Olimpianos", BigDecimal.valueOf(59));
        livros.forEach(System.out::println);

    }
    @Test
    void pesquisaComQuery(){
        var resultado = repository.listarTodos();
        resultado.forEach(System.out::println);
    }

    @Test
    void listaAutoresAmericanos(){
        var autores = repository.autoresNorteAmericano();
        autores.forEach(System.out::println);
    }

    @Test
    void listarGeneroParamTest(){
        var resultado = repository.findByGeneroLivro(GeneroLivro.HEROIS, "dataPublicacao");
        resultado.forEach(System.out::println);
    }
    @Test
    void deletePorGenero(){
        repository.deleteByGenero(GeneroLivro.FICCAO);
    }
    @Test //vai açterar a data de todos os livros, mas se eu quiser algo especifico é só mexer na query
    void updateDataPub(){
        repository.updateDataPublicacao(LocalDate.of(2000,5,22));
    }



}