package com.rail.response.system.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "MENSAGEM")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mensagem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mensagem_seq")
    @SequenceGenerator(name = "mensagem_seq", sequenceName = "mensagem_seq", allocationSize = 1)
    @Column(name = "id_mensagem")
    private Integer id;

    @Column(name = "conteudo", nullable = false, length = 4000)
    private String conteudo;

    @Column(name = "contato", length = 20)
    private String contato;

    @ManyToOne
    @JoinColumn(name = "USUARIO_id_usuario", nullable = false)
    private Usuario usuario;
}
