package com.rail.response.system.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ESTACAO")
public class Estacao extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estacao_seq")
    @SequenceGenerator(name = "estacao_seq", sequenceName = "estacao_seq", allocationSize = 1)
    @Column(name = "id_estacao")
    private Integer id;

    @Column(name = "nm", nullable = false)
    private String nome;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "dt_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "carro", length = 20)
    private String carro;
}
