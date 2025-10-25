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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;


    @Test
    public void salvarteste(){
        Autor autor = new Autor();
        autor.setNome("J.K Rowling");
        autor.setNacionalidade("Autraliano");
        autor.setDataNascimento(LocalDate.of(1978,11,29));
        repository.save(autor);



    }

    @Test
    public void atualizartest(){
        var id = UUID.fromString("6a0e310f-e712-4f21-9c2d-6f612eadc625");
        Optional<Autor> possivel=repository.findById(id);
        if (possivel.isPresent()){
            Autor autorencontrado = possivel.get();
            System.out.println("Autor encontrado");
        System.out.println("Possivel sutor encontrrado: "+ possivel);
        autorencontrado.setNome("Tom King");
        repository.save(autorencontrado); // vai atualizar o registro
            //no caso não vain rodar pq teria que ter os livros do autor, mas nesse caso rodaria se eu colocasse o @Transient
    }else {
            System.out.println("Autor não existe na base de dados");
        }

    }


    @Test
    public void contar(){
        System.out.println("Contagem de autores: "+repository.count());
    }
    @Test
    public void deletarid(){
        var id = UUID.fromString("6a0e310f-e712-4f21-9c2d-6f612eadc625");
        repository.deleteById(id);
    }

    @Test
    public void listartodos(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);

    }

    @Test
    void salvarautorlivro(){
        Autor autor = new Autor();
        autor.setNome("J.K Rowling");
        autor.setDataNascimento(LocalDate.of(1965,7,31));
        autor.setNacionalidade("Norte-Americano");


        Livro livro = new Livro();
        livro.setTitulo("Harry Potter e a Pedra Filosofal");
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setPreco(BigDecimal.valueOf(47,99));
        livro.setDataPublicacao(LocalDate.of(1997,6,26));
        livro.setIsbn("178040261");
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setTitulo("Harry Potter e a Câmara Secreta");
        livro2.setGenero(GeneroLivro.FANTASIA);
        livro2.setPreco(BigDecimal.valueOf(37,50));
        livro2.setDataPublicacao(LocalDate.of(1998,7,2));
        livro2.setIsbn("651047441");
        livro2.setAutor(autor);

        //tem como fazer em cascade all e não precisava usar o livro.repository.saveall
        //nesse caso faz sentido usar o cascade
        //relacionamento tomany normalmente é lazy





        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);
        repository.save(autor);
        livroRepository.saveAll(autor.getLivros());

    }
    @Test
    void listarautor(){
        var uuid = UUID.fromString("276b97e9-6fca-46d7-98de-8e5635758bd8");
        var autor=repository.findById(uuid).get();

        //buscar livros por autor
        List<Livro> byAutor = livroRepository.findByAutor(autor);
        autor.setLivros(byAutor);
        autor.getLivros().forEach(System.out::println);
    }

    //no eager quando carregar  autor traga sempre s livros, mas vai ficar muito pesada o caregamento, deixe lazy, porem já é o padrão
}
