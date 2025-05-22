package com.rail.response.system.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TIPO_NOTIFICACAO")
public class TipoNotificacao extends BaseEntity {

    @Id
    @SequenceGenerator(name = "tipo_notificacao_seq", sequenceName = "tipo_notificacao_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_notificacao_seq")
    @Column(name = "id_tp")
    private Integer id;

    @Column(name = "tp", nullable = false, length = 50)
    private String tp;

    @ManyToOne(optional = false)
    @JoinColumn(name = "NOTIFICACAO_id_notificacao", referencedColumnName = "id_notificacao", nullable = false)
    private Notificacao notificacao;
}
