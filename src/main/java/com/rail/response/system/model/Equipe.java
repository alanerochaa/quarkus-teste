package com.rail.response.system.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "EQUIPE")
public class Equipe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipe_seq")
    @SequenceGenerator(name = "equipe_seq", sequenceName = "equipe_seq", allocationSize = 1)
    @Column(name = "id_equipe")
    private Integer id;

    @Column(name = "nm_equipe", nullable = false, length = 50)
    private String nomeEquipe;

    @Column(name = "tp_experiencia", length = 100)
    private String tipoExperiencia;

    @Column(name = "contato_responsavel", length = 20)
    private String contatoResponsavel;
}
