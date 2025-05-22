package com.rail.response.system.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TAREFA")
public class Tarefa extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarefa_seq")
    @SequenceGenerator(name = "tarefa_seq", sequenceName = "tarefa_seq", allocationSize = 1)
    @Column(name = "id_tarefa")
    private Integer id;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descricao", length = 4000)
    private String descricao;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_id_usuario", nullable = false)
    private Usuario usuario;
}
