package com.rail.response.system.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "PASSAGEIRO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Passageiro extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passageiro_seq")
    @SequenceGenerator(name = "passageiro_seq", sequenceName = "passageiro_seq", allocationSize = 1)
    @Column(name = "id_passageiro")
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "documento", nullable = false, unique = true)
    private String documento;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "responsavel")
    private String responsavel;

    @Column(name = "contato")
    private String contato;
}
