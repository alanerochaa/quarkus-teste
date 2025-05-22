package com.rail.response.system.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NOTIFICACAO")
public class Notificacao extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificacao_seq")
    @SequenceGenerator(name = "notificacao_seq", sequenceName = "notificacao_seq", allocationSize = 1)
    @Column(name = "id_notificacao")
    private Integer id;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "tp_alerta", length = 50)
    private String tipoAlerta;

    @Column(name = "conteudo_notificacao", length = 4000)
    private String conteudo;

    @Column(name = "tp_notificacao", nullable = false, length = 50)
    private String tipoNotificacao;

    @Column(name = "operador", length = 100)
    private String operador;

    @Column(name = "st_notificacao", length = 50)
    private String status;

    @Column(name = "estacao", length = 100)
    private String estacao;

    @Column(name = "criticidade")
    private Integer criticidade;

    @Column(name = "prioridade")
    private Integer prioridade;

    @Column(name = "comentario_notificacao", length = 400)
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "EQUIPE_id_equipe")
    private Equipe equipe;

}
