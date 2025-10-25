package com.EstudosJpaSpring.livariaapi.repository;

import com.EstudosJpaSpring.livariaapi.model.Autor;
import com.EstudosJpaSpring.livariaapi.model.GeneroLivro;
import com.EstudosJpaSpring.livariaapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;



public interface LivroRepository extends JpaRepository<Livro, UUID> {
    boolean existsByAutor(Autor autor);
    //QUERY METHOD/USE O FINDBY QUANDO QUISER TRAZER DADOS DE ALGUMA IDENTIDADE, UM METODO ESPECIFICO DE CONSULTA
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);
    List<Livro> findByIsbn(String isbn);
    //AND
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);
    //OR
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);
    //BUSCCAR PEDAÇO DA STRING/CONTAING TBM É BOM

    //Quando uma coisa está errada no repositório tudo para de rodar
    //JPQL -> referencia as entidades e as propriedades, tem que esquecer de como esteja na coluna de dados a que pega é a que está no programa
    @Query("select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodos();

    @Query("""
            select l.genero
            from Livro l
            join l.autor a where a.nacionalidade = 'Norte-Americano'
            order by l.genero
            """)
    List<String> autoresNorteAmericano();

    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGeneroLivro(@Param("genero") GeneroLivro generoLivro, @Param("paramOrdenacao") String nomePropriedade);
    //como não ta fazendo pesquisa não precisa colocar retorno
    @Modifying // fazer operação escrita, precisa colocar o modifyng, dizendo que vai modificar registro
    @Transactional//abrir uma transação(insert, update, delete e etc), executar a operação de escrita
    @Query("""
            delete from Livro where genero = ?1
            
            """)
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1 ")
    void updateDataPublicacao(LocalDate novadata);

}
