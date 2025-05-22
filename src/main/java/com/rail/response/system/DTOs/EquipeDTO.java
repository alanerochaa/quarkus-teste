package com.rail.response.system.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquipeDTO {

    @Schema(examples = "1")
    @JsonProperty("id")
    private Integer id;

    @Schema(examples = "Equipe de Análise de Dados")
    @JsonProperty("nome_equipe")
    @NotBlank
    @Size(max = 50)
    private String nomeEquipe;

    @Schema(examples = "Inteligência Artificial")
    @JsonProperty("tipo_experiencia")
    @Size(max = 100)
    private String tipoExperiencia;

    @Schema(examples = "responsavel@test.com")
    @JsonProperty("contato_responsavel")
    @Size(max = 20)
    private String contatoResponsavel;

    @Schema(examples = "2024-05-20T15:30:00Z")
    @JsonProperty("data_criacao")
    private Date dataCriacao;

    @Schema(examples = "2024-05-21T10:15:00Z")
    @JsonProperty("data_atualizacao")
    private Date dataAtualizacao;
}
