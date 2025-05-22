package com.rail.response.system.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CONTATO_USUARIO")
@EqualsAndHashCode(callSuper = true)
public class Contato extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contato_seq")
    @SequenceGenerator(name = "contato_seq", sequenceName = "contato_seq", allocationSize = 1)
    @Column(name = "id_contato")
    private Long id;

    @Column(name = "valor_contato", nullable = false, length = 100)
    private String valorContato;

    @Column(name = "tp_contato", length = 50)
    private String tipoContato;

    // Relacionamento ManyToOne com Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_id_usuario", nullable = false)
    private Usuario usuario;

}
