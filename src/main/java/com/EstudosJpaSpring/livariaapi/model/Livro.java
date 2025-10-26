package com.EstudosJpaSpring.livariaapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
@ToString(exclude = "autor")//PARA NÃO IMPRIMIR O TOSTRING DO AUTOR
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(name="data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name="data_atualizacao")
    private LocalDateTime dataCAtualizacao;
    @Column(name="id_usuario")
    private UUID idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)//(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
