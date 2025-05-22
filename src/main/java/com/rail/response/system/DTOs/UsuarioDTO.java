package com.rail.response.system.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    @Schema(examples = "1")
    @JsonProperty("id_usuario")
    private Integer idUsuario;

    @NotNull
    @Schema(examples = "João da Silva")
    @JsonProperty("nome")
    @Size(max = 100)
    private String nome;

    @Schema(examples = "true")
    @JsonProperty("administrador")
    private boolean administrador;

    @Schema(examples = "NIVEL_1")
    @JsonProperty("nivel_interno")
    @Size(max = 20)
    private String nivelInterno;

    @Schema(examples = "USUARIO_COMUM")
    @JsonProperty("nivel_usuario")
    @Size(max = 20)
    private String nivelUsuario;

    @Schema(examples = "2024-01-01")
    @JsonProperty("data_criacao")
    private Date dataCriacao;

    @Schema(examples = "2024-05-22")
    @JsonProperty("data_atualizacao")
    private Date dataAtualizacao;

    @Schema(examples = "1")
    @JsonProperty("id_login")
    private Integer idLogin;
}
