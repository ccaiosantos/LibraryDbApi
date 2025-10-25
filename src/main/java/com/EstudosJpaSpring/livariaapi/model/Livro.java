package com.EstudosJpaSpring.livariaapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
@ToString(exclude = "autor")//PARA NÃO IMPRIMIR O TOSTRING DO AUTOR
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_livro", nullable = false)
    private UUID id;

    @Column(name = "isbn",length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo",length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;
    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco;
    @ManyToOne(fetch = FetchType.LAZY)//(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
