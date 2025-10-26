package com.EstudosJpaSpring.livariaapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = "ivro")//pode ser um array
public class Autor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    @CreatedDate
    @Column(name="data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name="data_atualizacao")
    private LocalDateTime dataCAtualizacao;
    @Column(name="id_usuario")
    private UUID idUsuario;


    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
            //cascade = CascadeType.ALL)
    private List<Livro> livros;

    public Autor(){

    }

}
