package com.rail.response.system.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "USUARIO")
public class Usuario extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", allocationSize = 1)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nm", nullable = false)
    private String nome;

    @Column(name = "e_admin", nullable = false, length = 1, columnDefinition = "CHAR(1)")
    private String adminFlag; // 'S' ou 'N'

    @Column(name = "nivel_interno", length = 20)
    private String nivelInterno;

    @Column(name = "nivel_usuario", length = 20)
    private String nivelUsuario;

    @OneToOne(optional = false)
    @JoinColumn(name = "LOGIN_id_login", nullable = false)
    private Login login;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "EQUIPE_id_equipe")
    private Equipe equipe;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contato> contatos;

    public boolean isAdministrador() {
        return "S".equalsIgnoreCase(this.adminFlag);
    }

    public void setAdministrador(boolean administrador) {
        this.adminFlag = administrador ? "S" : "N";
    }
}
